# TFG ISI - Jorge Sobrino Solís

## Installation
```bash
git clone jsobrinosolis/tfg-isi-jorgesobrinosolis
```

## Usage
**Java 11 must be used to compile this project.**

To launch the JADE platform, run the following command:

```bash
java -cp lib/jade.jar jade.Boot -gui
```
This will boot JADE and start a GUI for more accessibility.

Next, run the following sbt command to start the Play web service:

```bash
sbt compile && sbt run
```

