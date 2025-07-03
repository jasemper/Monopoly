FROM hseeberger/scala-sbt:graalvm-ce-21.3.0-java17_1.6.2_3.1.1
#FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1 
#RUN apt-get update && \ apt-get install -y sbt libxrender1 libxtst6 libxi6

WORKDIR /monopoly
ADD . /monopoly
CMD sbt run