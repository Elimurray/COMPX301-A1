# Multiway Merge Sort Java

`Alexander Trotter: 1644272`, `Eli Murray: 1626960`

## Instructions

### Intial compile

`javac XSort.java`

### Runs only

_this will output only the runs that have been made from the input seperated by `\n----\n`_

`cat {input-file} | java XSort.class {RunSize} > {outputfile}`

### Fully sorted

`cat {input-file} | java XSort.class {RunSize} {K-Size} > {outputfile}`

## Switches and Flags

### verbose (-v)

verbose has 3 levels

- `-v`: Info will just show infomation about the proram it self
- `-vv`: Debug level show all previouse and this will output infomation after writing runs and sorting runs
- `-vvv`: Trace show all previouse plus will show at each point in the sorting algorithim
