JOOQ_VERSION=3.11.9
MYSQL_JAR_VERSION=8.0.13

JOOQ_DIR=lib/jOOQ-$JOOQ_VERSION/jOOQ-lib
MYSQL_JAR_DIR=~/.m2/repository/mysql/mysql-connector-java/$MYSQL_JAR_VERSION

echo Removing files...
rm -rfv src/main/java/hu/waldorf/finance/generated

echo
echo Generating java files

java --add-modules java.xml.bind -classpath $JOOQ_DIR/jooq-$JOOQ_VERSION.jar:$JOOQ_DIR/jooq-meta-$JOOQ_VERSION.jar:$JOOQ_DIR/jooq-codegen-$JOOQ_VERSION.jar:$MYSQL_JAR_DIR/mysql-connector-java-$MYSQL_JAR_VERSION.jar:library.xml org.jooq.codegen.GenerationTool library.xml