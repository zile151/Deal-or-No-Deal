# Setup: Virtual Machine Setup

### Due: Check Course Calendar/Canvas

Through this and the following labs, you will gain hands-on experience with real-world network
programming.  You will write a program that allows your computer to communicate
with another, be it across the room or across the world. You will program a
router to deliver information to the right destination in a network with
thousands of nodes.  You will learn how decisions made by protocol designers
and network administrators affect Internet performance.  You will analyze data
from UT's own network to detect security threats and learn how to
prevent them.

These labs are designed to be interesting and rewarding at the end.
All of them are manageable in the time allotted. If you have questions, 
want to suggest clarifications, please come to office hours, ask questions on Piazza, 
or talk to an instructor after class.

For all of the labs, you may work with a partner of your choice. You may also work solo. 
**You are not allowed to copy or look at code from other students beyond your team. 
Also do note that once the team is formed, you may not depart the team as you and 
your partner would have seen each other's code already.** 
You are welcome to discuss the labs with other students beyond your team without sharing code.

Let's get started!

## Set Up Virtual Machine

The first part of this lab is to set up the virtual machine (VM) you
will use for the rest of the labs. This will make it easy to install all
dependencies for the programming assignments, saving you the tedium of
installing individual packages and ensuring your development environment is
correct.

### Step 1: Install Vagrant

Vagrant is a tool for automatically configuring a VM using instructions given
in a single "Vagrantfile."

**macOS & Windows:** You need to install Vagrant using the correct download
link for your computer here: https://www.vagrantup.com/downloads.html.

**Windows only**: You will be asked to restart your computer at the end of the
installation. Click Yes to do so right away, or restart manually later, but
don't forget to do so or Vagrant will not work!

**Linux:** First, make sure your package installer is up to date by running the
command `sudo apt-get update`. To install Vagrant, you must have the "Universe"
repository on your computer; run `sudo apt-add-repository universe` to add it.
Finally, run `sudo apt-get install vagrant` to install vagrant.

### Step 2: Install VirtualBox

VirtualBox is a VM provider (hypervisor).

**macOS & Windows:** You need to install VirtualBox using the correct download
link for your computer here: https://www.virtualbox.org/wiki/Downloads. The
links are under the heading "VirtualBox 5.x.x platform packages."

**Windows only:** Use all the default installation settings, but you can
uncheck the "Start Oracle VirtualBox 5.x.x after installation" checkbox.

**Linux:** Run the command `sudo apt-get install virtualbox`.

**Note:** This will also install the VirtualBox application on your computer,
but you should never need to run it, though it may be helpful (see Step 6).


### Step 3: Install X Server

You will need an X Server to input commands to the virtual machine.

**macOS:** Install [XQuartz](https://www.xquartz.org/). You will need to log
out and log back in to complete the installation (as mentioned by the prompt at
the end).

**Windows:** Install
[Xming](https://sourceforge.net/projects/xming/files/Xming/6.9.0.31/Xming-6-9-0-31-setup.exe/download).
Use default options and uncheck "Launch Xming" at the end.

**Linux:** The X server is pre-installed!

### Step 4: Clone course Git repository

Open your terminal and `cd`
to wherever you want to keep files for this course on your computer.  

Run `git clone https://github.com/cs356-sp21/lab-{teamname}` to
download the starter files from GitHub.

`cd lab-{teamname}/assignments` to enter the lab directory.

### Step 5: Download Pre-built VirtualBox Image and Provision using Vagrant

From the `assignments` directory you just entered, run below command to
download the pre-built Virtual Box image for this lab. 

`wget https://cs.utexas.edu/\~mhan/courses/cs356/sp21/labs/lab0/cs356-sp21-minilab.box`

If you do not have wget, you can also manually download using any web browser
using the same URL given above without "wget". Do make sure to place this box file 
under `assignments` directory.

After downloading, again from the `assignments` directory, run the command 
`vagrant up` to start the VM and provision it according to the Vagrantfile. 

**Note 1**: The following commands will allow you to stop the VM at any point
(such as when you are done working on an assignment for the day):
* `vagrant suspend` will save the state of the VM and stop it.
* `vagrant halt` will gracefully shutdown the VM operating system and power
  down the VM.
* `vagrant destroy` will remove all traces of the VM from your system. If you
  have important files saved on the VM (like your assignment solutions) **DO
  NOT** use this command.

Additionally, the command `vagrant status` will allow you to check the status
of your machine in case you are unsure (e.g. running, powered off, saved...).
You must be in some subdirectory of the directory containing the Vagrantfile to
use any of the commands above, otherwise Vagrant will not know which VM you are
referring to.

**Note 2**: The VirtualBox application that was installed in Step 2 provides a
visual interface as an alternative to these commands, where you can see the
status of your VM and power it on/off or save its state. It is not recommended
to use it, however, since it is not integrated with Vagrant, and typing
commands should be no slower. It is also not an alternative to the initial
`vagrant up` since this creates the VM.

### Step 6: Test SSH to VM and take the first screenshot

Run `vagrant ssh` from your terminal. This is the command you will use every
time you want to access the VM. If it works, your terminal prompt will change
to `vagrant@cs356:~$`. All further commands will execute on the VM. You can
then run `cd /vagrant` to get to the course directory that's shared between
your regular OS and the VM.

Now take a screenshot of your VM terminal and let's call this `Screenshot 1`.
This should show the result of running `vagrant ssh` from your host terminal.
The screenshot will look something like [this](../figs/screenshot1_example.png).

Vagrant is especially useful because of this shared directory structure.  You
don't need to copy files to and from the VM. Any file or directory in the
`assignments` directory where the `Vagrantfile` is located is automatically
shared between your computer and the virtual machine. This means you can use
your IDE of choice from outside the VM to write your code (but will still have
to build and run within the VM).

The command `logout` will stop the SSH connection at any point.

### Extra Note for Windows users

Line endings are symbolized differently in DOS (Windows) and Unix
(Linux/MacOS). In the former, they are represented by a carriage return and
line feed (CRLF, or "\r\n"), and in the latter, just a line feed (LF, or "\n").
Given that you ran `git pull` from Windows, git detects your operating system
and adds carriage returns to files when downloading. This can lead to parsing
problems within the VM, which runs Ubuntu (Unix). Fortunately, this only seems
to affect the shell scripts (\*.sh files) we wrote for testing. The
`Vagrantfile` is set to automically convert all files back to Unix format, so
**you shouldn't have to worry about this**. **However**, if you want to
write/edit shell scripts to help yourself with testing, or if you encounter
this problem with some other type of file, use the preinstalled program
`dos2unix`. Run `dos2unix [file]` to convert it to Unix format (before
editing/running in VM), and run `unix2dos [file]` to convert it to DOS format
(before editing on Windows). A good hint that you need to do this when running
from the VM is some error message involving `^M` (carriage return). A good hint
you need to do this when editing on Windows is the lack of new lines. Remember,
doing this should only be necessary if you want to edit shell scripts.

### Step 7: Open jupyter notebook for Lab0 and take the second (last) screenshot

[Minilab0 README](../minilab0/README.md) has instruction to open `Lab0_notebook.ipynb`. 
After opening the Jupyter notebook, run the python code cell in Part A 
that starts with ```from mininet.topo import Topo```. You should see "setup finished" message
being printed out to the output cell without any errors.

Take the final screenshot `Screenshot 2` that shows the cell and the output. 
After taking the screenshot remove the line that prints "setup finished" from the cell.

### Step 8: Submit 2 screenshots to Canvas

You must submit 2 screenshots to Canvas, each of which taken in Step 6 
and Step 7 respectively. 
 
### Step 10: Now you are ready to get started on assignments/minilab0

