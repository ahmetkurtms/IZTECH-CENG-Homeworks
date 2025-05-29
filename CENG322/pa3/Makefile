CC=gcc
CFLAGS=-Wall -Wextra -pthread
TARGET=scheduler

all: $(TARGET)

$(TARGET): scheduler.c scheduler.h
	$(CC) $(CFLAGS) -o $(TARGET) scheduler.c

clean:
	rm -f $(TARGET) 