java -cp feed-services-8.8.1.RELEASE.jar -Dskava.redis.maxHeap=2048M -Dcamel.logging.level.root=info -Dspring.profiles.active=dev -Dspring.cloud.zookeeper.enabled=false -Dspring.cloud.zookeeper.config.watcher.enabled=false -Dloader.path=target\final org.springframework.boot.loader.PropertiesLauncher -debug
