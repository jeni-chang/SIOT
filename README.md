# SD-IOT

## Run Configuration
1. Link all libraries in `SD_IOT\lib` to your project.
2. Modify GML files path in `SD_IOT\src\device\Executor.java`.
```java
final String INS_GML = "Your_Path\\setcover\\gml\\IowaStatewideFiberMap.gml";
```
3. Increase application [heap size] in run configuration.
```
-Xmx4096m
```

## JGraphT
* JGraphT Version used: 1.01
* Some methods used in this projetct had been markded as `Deprecated` by the latest [JGraphT API].
* e.g. `GmlImporter` and `VertexProvider` in `SD_IOT\src\graph\Topo.java`.

## Useful Links
- [Github JGraphT Wiki](https://github.com/jgrapht/jgrapht/wiki)
- [JGraphT Javadocs](http://jgrapht.org/javadoc/)
- [Github Guava Wiki](https://github.com/google/guava/wiki)
- [Topology Zoo](http://www.topology-zoo.org/dataset.html)
- [物件相等性](https://openhome.cc/Gossip/JavaEssence/ObjectEquality.html)
- [Generate equals() and hashCode() with Eclipse](http://www.baeldung.com/java-eclipse-equals-and-hashcode)
- [A Guide to Naming Variables](https://a-nickels-worth.blogspot.tw/2016/04/a-guide-to-naming-variables.html)

## Stack Overflow
- [How to find an object in an ArrayList by property](https://stackoverflow.com/questions/17526608/how-to-find-an-object-in-an-arraylist-by-property)
- [Iterate through a HashMap](https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap)
- [How to refer to custom Vertexes to add an Edge, using JGraphT](https://stackoverflow.com/questions/23837638/how-to-refer-to-custom-vertexes-to-add-an-edge-using-jgrapht)
- [Measure execution time for a Java method](https://stackoverflow.com/questions/3382954/measure-execution-time-for-a-java-method)

[heap size]: https://stackoverflow.com/questions/15313393/how-to-increase-application-heap-size-in-eclipse
[JGraphT API]: http://jgrapht.org/javadoc/deprecated-list.html