import com.squareup.javapoet.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ClassGenerator {

    public static String path;
    public static void start() throws IOException {
        generateActionService();
        generateCallService();
        generateMainVerticle();
    }


    static void generateCallService() throws IOException {
        String resourceName = "command.txt";
        ClassLoader classLoader = ClassGenerator.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
        String absolutePath = file.getAbsolutePath();

        StringBuilder switchContext = new StringBuilder(" switch (action) {\n");

        Files.readAllLines(Path.of(absolutePath)).forEach(line -> {
            String actionName = line.split("\\(")[0];
            switchContext.append("case \"" + actionName + "\":" + " actionService." + actionName + "(rs);\n" +
                    "break;\n");
        });
        switchContext.append("}");


        MethodSpec main = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .returns(void.class)
                .addParameter(RoutingContext.class, "rs")
                .addParameter(String.class, "action")
                .addStatement(switchContext.toString())
                .build();



        FieldSpec field = FieldSpec.builder(ClassName.get("", "ActionService"), "actionService")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .build();

        TypeSpec callService = TypeSpec.classBuilder("CallService")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(main)
                .addField(field)
                .build();

        JavaFile javaFile = JavaFile.builder("", callService)
                .build();


        javaFile.writeTo(Path.of(path));
    }

    static void generateActionService() throws IOException {
        String resourceName = "command.txt";
        ClassLoader classLoader = ClassGenerator.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
        String absolutePath = file.getAbsolutePath();

        List<MethodSpec> methodSpecs = new ArrayList<>();
        Files.readAllLines(Path.of(absolutePath)).forEach(line -> {
            String actionName = line.split("\\(")[0];
            System.out.println("Added end-point: /" + actionName);
            methodSpecs.add(MethodSpec.methodBuilder(actionName)
                    .addParameter(RoutingContext.class, "rs")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .build());
        });

        TypeSpec actionService = TypeSpec.interfaceBuilder("ActionService")
                .addModifiers(Modifier.PUBLIC)
                .addMethods(methodSpecs)
                .build();

        JavaFile javaFile = JavaFile.builder("", actionService)
                .build();

        javaFile.writeTo(Path.of(path));
    }

    static void generateMainVerticle() throws IOException {


        String methodContent = "\n" +
                "\n" +
                "        router.get(String.format(\"%s:action\", \"/\")).handler(rc -> {\n" +
                "            CallService.get(rc, rc.pathParam(\"action\"));\n" +
                "        });\n" +
                "\n" +
                "\n" +
                "        vertx.createHttpServer()\n" +
                "                .requestHandler(router)\n" +
                "                .listen(8080, \"localhost\", res -> {\n" +
                "                    if (res.succeeded()) {\n" +
                "                        System.out.println(\"Server started\");\n" +
                "                    } else if (res.failed()) {\n" +
                "                        System.out.println(\"Server failed\");\n" +
                "                    }\n" +
                "                });";

        MethodSpec methodSpec = MethodSpec.methodBuilder("start")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addStatement("Router router = $T.router(vertx)", Router.class)
                .addStatement(methodContent)
                .build();


        TypeSpec actionService = TypeSpec.classBuilder("MainVerticle")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .superclass(AbstractVerticle.class)
                .build();


        JavaFile javaFile = JavaFile.builder("", actionService)
                .build();


        javaFile.writeTo(Path.of(path));


    }

}
