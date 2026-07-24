# techvika-kafka-producer-ms

# Kafka Version
kafka-topics.bat --version

# Kafka Cluster Creation
kafka-storage.bat random-uuid

# Prepare Storage directory in running Kraft mode/Format
kafka-storage.bat format -t lq5QS4AyRf-kWJq4_XTnpQ -c config\server-1.properties

# Run Kafka Server
kafka-server-start.bat config\server-1.properties

# Stop Kafka Server  
kafka-server-stop.bat

# Create Topic
kafka-topics.bat --create --topic techvika-topic-2 --partitions 3 --replication-factor 3 --bootstrap-server localhost:9092,localhost:9094,localhost:9096

# List down topics
kafka-topics.bat --list --bootstrap-server localhost:9092

# Describe Topic  
kafka-topics.bat --describe --bootstrap-server localhost:9092

# Produce message to Topic [commandline]
# Without key
kafka-console-producer.bat --bootstrap-server localhost:9092,localhost:9094,localhost:9096 --topic techvika-topic-1

# With key
kafka-console-producer.bat --bootstrap-server localhost:9092,localhost:9094,localhost:9096 --topic techvika-topic-1 --property "parse.key=true" --property "key.separator=:"

# Consume message from Topic [commandline]
kafka-console-consumer.bat --topic user-created-events-topic-1 --from-beginning --bootstrap-server localhost:9092,localhost:9094