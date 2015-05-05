# foxcoinkeeper

terminal commands
Update-
$ sudo apt-get update

Download the source
$ sudo apt-get install git
$ mkdir -p src && cd src
$ git clone https://github.com/bitcoin/bitcoin.git

Install dependencies
$ ls bitcoin
$ sudo apt-get install build-essential
$ wget http://download.oracle.com/berkeley-db/db-4.8.30.NC.tar.gz
$ echo '12edc0df75bf9abd7f82f821795bcee50f42cb2e5f76a6a281b85732798364ef  db-4.8.30.NC.tar.gz' | sha256sum -c
$ tar -xvf db-4.8.30.NC.tar.gz
$ cd db-4.8.30.NC/build_unix
$ mkdir -p build
$ BDB_PREFIX=$(pwd)/build
$ ../dist/configure --disable-shared --enable-cxx --with-pic --prefix=$BDB_PREFIX
$ make install
$ cd ../..
$ sudo apt-get install autoconf libboost-all-dev libssl-dev libprotobuf-dev protobuf-compiler libqt4-dev libqrencode-dev libtool

Compile Bitcoin Core
$ cd bitcoin
$ git checkout v0.10.0
$ ./autogen.sh
$ ./configure CPPFLAGS="-I${BDB_PREFIX}/include/ -O2" LDFLAGS="-L${BDB_PREFIX}/lib/" --with-gui
$ make
$ sudo make install

Run Bitcoin Core
$ bitcoin-qt


