buildscript {
	dependencies {
		classpath("org.flywaydb:flyway-database-oracle:10.7.2")
	}
}


plugins {
	java
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.flywaydb.flyway") version "10.7.2"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.oracle.database.jdbc:ojdbc11")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.3")
	implementation("org.springframework.boot:spring-boot-starter-batch")
	implementation("org.springframework.batch:spring-batch-test")
//	implementation("org.slf4j:slf4j-api:1.7.30")
//	implementation("org.slf4j:slf4j-simple:1.7.30")
	implementation("ch.qos.logback:logback-core:1.4.14")
	implementation("ch.qos.logback:logback-classic:1.4.14")
	implementation ("org.flywaydb:flyway-core:10.7.2")
	runtimeOnly("org.flywaydb:flyway-database-oracle:10.7.2")
}

flyway {
	url = "jdbc:oracle:thin:@//localhost:1521/pdb01"
	user = "hogeuser"
	password = "passw0rd"
	locations = arrayOf("filesystem:src/main/resources/migration")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
