
.SUFFIXES: .java .class

.java.class:
	javac -O $<

CLASSES = vncviewer.class rfbProto.class authenticationPanel.class \
	  vncCanvas.class optionsFrame.class clipboardFrame.class \
	  animatedMemoryImageSource.class DesCipher.class

all: $(CLASSES) vncviewer.jar

vncviewer.jar: $(CLASSES)
	jar cf $@ $(CLASSES)
