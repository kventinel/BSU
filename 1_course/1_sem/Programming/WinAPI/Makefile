HEADERS = include/resource.h include/callbacks.h # and other headers
OBJS =	obj/resource.o obj/winmain.o obj/callbacks.o # obj/file.o for every src/file.cpp
APP_NAME = Win32App.exe
INCLUDE_DIRS = -I./include

CXX = i686-w64-mingw32.static-g++
LD = i686-w64-mingw32.static-g++
RC = i686-w64-mingw32.static-windres

LIBS = -lgdi32 -luser32 -lkernel32 -lcomctl32
LDFLAGS = -s -Wl,--subsystem,windows $(LIBS)
CXXFLAGS= -std=c++11 -g3 -D UNICODE -D _UNICODE -D _WIN32_IE=0x0500 -D WINVER=0x500 -Wall -Wextra


all: $(APP_NAME)

$(APP_NAME): ${OBJS}
	${LD} -o "$@" ${OBJS} ${LDFLAGS}

clean:
	rm obj/*.o $(APP_NAME)

obj/%.o: src/%.cpp ${HEADERS}
	${CXX} ${CXXFLAGS} ${INCLUDE_DIRS} -c $< -o $@

# Optional
obj/resource.o: res/resource.rc res/Application.manifest res/Application.ico include/resource.h
	${RC} ${INCLUDE_DIRS} -I./res -i $< -o $@
