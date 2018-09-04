# Operational instructions

to run execute

`cat Loans.csv | java -jar target/uberjar/loan-aggregator-0.1.0-SNAPSHOT-standalone.jar`

from the terminal

jar is included for convenience, but can be built using

`./lein uberjar`

no extra newlines in the input file after the last line is assumed

# Why Clojure?

Clojure was mainly chosen as it's quick to create a an application using the REPL to do live coding and also minimal LOC
are needed.
Being dynamically typed it's good for rapid prototyping.
It also runs on the JVM and has access to it's standard libs, which is widely accepted by enterprises.
It includes a framework for unit testing baked in.

# Performance considerations

Should the file be large line-seq a function that returns the lines of text from the input as a lazy sequence was used which
will reduce the memory footprint.
When writing to the output file the file is only opened once which should also increase performance.
