all: default

compile:
	$(MAKE) -j clean
	javac MIPS/MIPS.java

clean:
	$(RM) *.class
	$(RM) */*.class
	$(RM) */*/*.class

default:
	$(MAKE) -j compile
	java MIPS/MIPS inst.txt data.txt config.txt result.txt

1:
	$(MAKE) -j compile
	java MIPS/MIPS inst1.txt data.txt config.txt result.txt

2:
	$(MAKE) -j compile
	java MIPS/MIPS inst2.txt data.txt config.txt result.txt

3:
	$(MAKE) -j compile
	java MIPS/MIPS inst3.txt data.txt config.txt result.txt

4:
	$(MAKE) -j compile
	java MIPS/MIPS inst4.txt data.txt config.txt result.txt
