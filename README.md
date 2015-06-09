foxcoinkeeper is an application for bitcoin mining on Linux(tested on Ubuntu 14.04 LTS). 

foxcoinkeeper uses the underlying mining engine (bfgminer) to detect available mining devices and then presents options to user to activate the chosen devices for bitcoin mining. foxcoinkeeper is also able to relaunch if a device (node)is crushed.

Right now, the way it works is it establishes a master node. Then other nodes are branched under that node. When foxcoinkeeper is activated the nodes start listening for commands. They are assigned a unique id, and before launching it is checked according to their id that which nodes are listening by using proxy. On that new list of nodes the commands will be assigned, and the nodes can be launched. Once launched, the nodes can continue mining even if its master node is crushed or rebooted. 
