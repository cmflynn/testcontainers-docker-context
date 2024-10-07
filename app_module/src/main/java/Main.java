import java.nio.file.Path;
import java.nio.file.Paths;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

// make sure when running this your intellij run config working dir is this modules dira
// sorry that it requires intellij
public class Main {
    public static void main(String[] args) {
        Path dockerfile = Paths.get("./Dockerfile");

        GenericContainer<?> c1 = new GenericContainer<>(
                new ImageFromDockerfile()
                        .withFileFromFile(
                                "some-external-file.txt",
                                Paths.get("/Users/cflynn/IdeaProjects/testcontainers-docker-context/"
                                        + "external_app_module/some-external-file.txt").toFile()
                        )
                        .withDockerfile(dockerfile)
        )
                .withCommand("/bin/bash", "-c", "while true; do sleep 1000; done")
                .withCreateContainerCmdModifier(cmd -> cmd.withName("withFileFromFile"));

        c1.start();

        GenericContainer<?> c2 = new GenericContainer<>(
                new ImageFromDockerfile()
                        .withFileFromPath(
                                ".",
                                Paths.get("/Users/cflynn/IdeaProjects/testcontainers-docker-context/"
                                        + "external_app_module/some_external_dir")
                        )
                        .withDockerfile(dockerfile)
        )
                .withCommand("/bin/bash", "-c", "while true; do sleep 1000; done")
                .withCreateContainerCmdModifier(cmd -> cmd.withName("withFileFromPath"));

        c2.start();

        System.out.println("hello");
    }
}
