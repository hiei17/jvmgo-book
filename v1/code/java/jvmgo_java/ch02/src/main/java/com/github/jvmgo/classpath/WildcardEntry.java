package com.github.jvmgo.classpath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
//*通配符  匹配目录下全部jar包
class WildcardEntry  implements Entry  {

    private  CompositeEntry compositeEntry;

    WildcardEntry(String path) {
        toCompositeEntry(path);
    }

    private  void toCompositeEntry(String wildcardPath) {
        String baseDir = wildcardPath.replace("*", ""); // remove *
        try {
            String pathList = Files.walk(Paths.get(baseDir))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(p -> p.endsWith(".jar") || p.endsWith(".JAR"))
                    .collect(Collectors.joining(File.pathSeparator));
             compositeEntry=   new  CompositeEntry( pathList);
        } catch (IOException e) {
            //e.printStackTrace(System.err);
        }
    }

    @Override
    public byte[] readClass(String className) throws Exception {
        return compositeEntry.readClass(className);
    }
}
