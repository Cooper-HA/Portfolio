import subprocess
import multiprocessing
import RPi.GPIO as GPIO
import random
from time import sleep
import pyttsx3
import webbrowser
import os
import time
from pygame import mixer
import cv2
from datetime import datetime
import random
import speech_recognition as sr
import tkinter as tk
import pickle
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email import encoders
from tkinter import filedialog
import shutil
import requests 
import bs4
from signal import signal, SIGTERM, SIGHUP, pause
from rpi_lcd import LCD
from mfrc522 import SimpleMFRC522
import Adafruit_DHT
global audio
global audio1
import re
import argparse
from luma.led_matrix.device import max7219
from luma.core.interface.serial import spi, noop
from luma.core.render import canvas
from luma.core.virtual import viewport
from luma.core.legacy import text, show_message
from luma.core.legacy.font import proportional, CP437_FONT, TINY_FONT, SINCLAIR_FONT, LCD_FONT

audio = []
audio1 = []
'''
lcd = LCD()
def safe_exit(signum, frame):
    exit(1)
'''
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(18, GPIO.OUT)
GPIO.setup(26, GPIO.IN)
GPIO.setup(6, GPIO.IN)
GPIO.setup(16, GPIO.IN)
GPIO.setup(13, GPIO.IN)
mixer.init()
f=0
engine = pyttsx3.init()
rate = 130
engine.setProperty('rate', rate)
d=1
def main():
    '''
    reader = SimpleMFRC522()

    try:
        id, text = reader.read()
        print(id)
        if id ==  189685910117 or id ==  1044642077269 or id == 443646618983 or id == 4886718208 or id == 853038215265:
           print('ran')
           run('cooper')
        if text =='sam':
           run('sam')
        if text =='kellton':
           run('kellton')
        if text =='425706':
           run('nolan')
    except KeyboardInterrupt:
        print("invalid")
        quit()
    '''
    run('cooper')
def run(user='data'):
    global speaktext
    global rate
    global vol
    global mailpass
    d=1
    '''
    log=[]
    while True:
        if GPIO.input(26):
            GPIO.output(18, False)
        else:
            log.append('red')
        if GPIO.input(16):
            GPIO.output(18, False)
        else:
            log.append('yellow')
        if GPIO.input(13):
            GPIO.output(18, False)
        else:
            log.append('green')
        if GPIO.input(6):
            GPIO.output(18, False)
        else:
            log.append('blue')
        print(log)
        sleep(1)
        if str(log) == "['red', 'blue', 'green']":
            break
    print(log)
    '''
    #login()
    while True:
        a=rec(3)
        try:
            a=a.split()
        except:
            a = 'none'
        print(a)
        f=open('settings/'+user+'.obj', 'rb')
        speaktext=pickle.load(f)
        rate=pickle.load(f)
        try:
            mailpass=pickle.load(f)
        except:
            print("you do not have an email pass word for the use of this program")
        f.close()

        # getting details of current speaking rate                  #printing current voice rate
        engine.setProperty('rate', rate)
        f=1
        if a[0] in ['tony', 'toni', 'Tony', 'Toni']:
            engine.say(speaktext)
            engine.runAndWait()
            while True:
                try:
                    a=rec(3)
                    print(a)
                    bater=a
                    try:
                        a=a.split()
                    except:
                        pass
                    if a == None:
                        engine.say("didn't get that")
                        engine.runAndWait()
                        a="sorry"
                    f=1
                    d=1
                    if a[0] in ['how', 'why', 'what', 'is', 'do']:
                        for i in range(len(a)):
                                if a[i] in ['weather']:
                                    w=weather()
                                    engine.say(w)
                                    engine.runAndWait()
                                    d=0
                                    f=1
                                if a[i] in ['you', 'You', 'you?', 'You?', 'Your', 'your']:
                                    f=0
                                    for i in range(len(a)):
                                        d=0
                                        f=0
                                        if a[i] in ['eyes']:
                                                engine.say("blue")
                                                engine.runAndWait()
                                                f=1
                        if d == 1:
                            webbrowser.open('https://google.com/search?q=' + bater)
                        if f==0:
                            engine.say("that is classified")
                            engine.runAndWait()
                    try:
                        if a[0] in ['play', 'Play'] and a[1] not in['a']:
                            bater=bater.split("play")
                            bater=bater.split("Play")
                            bater.pop(0)
                            print(bater)
                            bater=str(bater)
                            if bater in['[]']:
                                play()
                            else:
                                play(bater)
                                print(bater)
                    except:
                        if a[0] in ['play', 'Play'] and a[1] not in['a']:
                            bater = str(bater)
                            bater=bater.split("play")
                            bater = str(bater)
                            bater=bater.split("Play")
                            #bater.pop(0)
                            print(bater)
                            bater=str(bater)
                            if bater in['[]']:
                                play()
                            else:
                                play(bater)
                                print(bater)
                    try:
                        if a[0] in ['play'] and a[1] in['a']:
                            video()
                    except:
                        pass
                    if a[0] in ['stop']:
                        mixer.music.pause()
                    if a[0] in ['email', 'contact']:
                        a.pop(0)
                        engine.say("please write mesage")
                        engine.runAndWait()
                        b = input(":")
                        print(b)
                        try:
                            emy(a[0], b, mailpass)
                        
                        except:
                            print("sorry")
                            engine.say("sorry...no can do")
                            engine.runAndWait()
                    if a[0] in ['send']:
                        try:
                            files(a[1])
                        except:
                            print("sorry")
                            engine.say("sorry...no can do")
                            engine.runAndWait()
                    if a[0] in ['hack', 'CD', 'LS']:
                        os.system('cd')
                        engine.say("right away sir")
                        engine.runAndWait()
                        try:
                            if a[1] in ['for']:
                                z=4
                            else:
                                z=2
                        except:
                            z=2.5
                        while True:
                            if a [0]not in['LS']:
                                a=rec(z)
                                print(a)
                            if a in ['stop']:
                                os.chdir('/')
                                os.chdir('home')
                                os.chdir('pi')
                                os.chdir("code")
                                os.chdir("python")
                                os.chdir("jarvis")
                                break
                            a=str(a.lower())
                            try:
                                os.chdir(a)
                                if a in ['jarvis']:
                                    engine.say('you are now messing with my brains... please be careful')
                                    engine.runAndWait()
                                else:
                                    engine.say(a)
                                    engine.runAndWait()
                            except FileNotFoundError:
                                os.system(a)
                    if a[0] in ['thank'] and a[1] in ['you']:
                        engine.say("you are welcome")
                        engine.runAndWait()
                    if a[0] in ['toni','tony','Toni','Tony']:
                        engine.say(speaktext)
                        engine.runAndWait()
                    if a[0] in ['time', 'Time']:
                        a=time2()
                        engine.say(a)
                        engine.runAndWait()
                    if a[0] in ['quit']:
                        f=open('settings/'+user+'.obj', 'wb')
                        pickle.dump(speaktext, f)
                        pickle.dump(rate, f)
                        pickle.dump(mailpass, f)
                        f.close()
                        GPIO.cleanup()
                        exit()

                    if a[0] in ['settings', 'Settings']:
                        root = tk.Tk()
                        app = Application(master=root)
                        app.mainloop()
                    if a[0] in ['mute']:
                        a=input()
                        if a =='unmute':
                            print('unmuted')
                    if a[0] in ['bro', ' Bro']:
                        engine.say("bro")
                        engine.runAndWait()
                    if a[0] in ['launch', 'open']:
                        try:
                                if a[1] in ['Terminal', 'termanal']:
                                    subprocess.Popen("lxterminal")
                                if a[1] in ['calculator', 'math']:
                                    subprocess.Popen("galculator")
                                if a[1] in ['menu']:
                                    subprocess.Popen("alacarte")
                                if a[1] in ['python', 'Python']:
                                    subprocess.Popen("idle-python3.7")
                                if a[1] in ['files', 'file']:
                                    subprocess.Popen("pcmanfm")
                                    signal(SIGTERM, safe_exit)
                                    signal(SIGHUP, safe_exit)
                                    lcd.text("files", 2)
                                if a[1] in ['tasks', 'task']:
                                    subprocess.Popen("lxtask")
                                if a[1] in ['system']:
                                    os.system('sudo raspi-config')
                        except e:
                            print(e)
                    if a[0] in ['shutdown']:
                        GPIO.cleanup()
                        os.system("sudo poweroff")
                    if a[0] in ['rover' 'Rover']:
                        PWM=Motor()
                        try:
                            if a[1] in ['fd']:
                                try:
                                    PWM.setMotorModel(2000,2000,2000,2000)
                                    for i in range(1,int(a[2])+1):
                                        time.sleep(1)
                                        print(i)
                                except:
                                    PWM.setMotorModel(2000,2000,2000,2000)
                                    a=input("press enter to stop")
                            if a[1] in ['bk']:

                                try:
                                    PWM.setMotorModel(-2000,-2000,-2000,-2000)
                                    for i in range(1,int(a[2])+1):
                                        time.sleep(1)
                                        print(i)
                                except:
                                    PWM.setMotorModel(-2000,-2000,-2000,-2000)
                                    a=input("press enter to stop")
                            if a[1] in ["rt", "right"]:
                                try:
                                    PWM.setMotorModel(-500,-500,2000,2000)
                                    for i in range(1,int(a[2])+1):
                                        time.sleep(1)
                                        print(i)
                                except:
                                    PWM.setMotorModel(-500,-500,2000,2000)
                                    a=input("press enter to stop")
                            if a[1] in ['lt', "left"]:
                                try:
                                    PWM.setMotorModel(2000,2000,-500,-500)
                                    for i in range(1,int(a[2])+1):
                                        time.sleep(1)
                                        print(i)
                                except:
                                    PWM.setMotorModel(2000,2000,-500,-500)
                                    a=input("press enter to stop")
                            if a[1] in ["cam", "camera"]:
                                camera = PiCamera()

                                camera.start_preview()
                                print("camera on")
                                time.sleep(5)
                                camera.stop_preview()
                        except IndexError:
                            print("Rover:\nthis is a function to control the rover\nfd...............move the rover forward\nbk...............move the rover backward\nright/left.......move the rover to the right/left\ncam..............open the camera   new line  \nproper syntax:\nrover fd (number of seconds)")
                        finally:    
                            PWM.setMotorModel(0,0,0,0)
                                                        
                except KeyboardInterrupt:
                    pass

def rec2(audio):
    r = sr.Recognizer()
    a = r.recognize_google(audio)
    global bunt
    bunt=a
def rec3():
    sr.Microphone.list_microphone_namesss()
    mic = sr.Microphone(device_index=10)
    with mic as source:
        #r.adjust_for_ambient_noise(source)
        audio = r.record(source, duration=c)
def rec(c, e=1):
    global bunt
    dev=9
    r = sr.Recognizer()
    sr.Microphone.list_microphone_names()
    try:
        mic = sr.Microphone(device_index=dev)
    except:
        if dev == 11:
            mic = sr.Microphone(device_index=9)
        else:
            mic = sr.Microphone(device_index=4)
    with mic as source:
        #r.adjust_for_ambient_noise(source)
        audio = r.record(source, duration=c)
    try:
        first = multiprocessing.Process(target = rec2(audio), args=())
        first.start()
        return bunt
    except:
        os.system('clear')
        if e == 5:
            e = 1
        if e == 1:
            print("loding")
        if e == 2:
            print("loding.")
        if e == 3:
            print("loding..")
        if e == 4:
            print("loding...")
        return rec(c,dev, e+1)

def emya(files, person,  mailpass):
    mail_content = 'Hello'
    #The mail addresses and password
    sender_address = 'cooperhaddick@gmail.com'
    sender_pass = mailpass
    if person == 'mom' or 'Mom':
        a=['nahaddick@gmail.com']
    elif person == 'Sam':
        a=['sammichaelflynn@gmail.com']
    elif person=='Kelton':
        a=['kelltonholzer@gmail.com']
    elif person=='Nolan':
        a=['nolan.holzer@gmail.com']
    elif person=='me':
        a=['cooperhaddick@gmail.com']
    elif person == 'alli':
        a=['abhaddick@gmail.com']
    else:
        a=None
        print("bad")
    receiver_address = a
    #Setup the MIME
    message = MIMEMultipart()
    message['From'] = sender_address
    message['To'] = receiver_address
    message['Subject'] = 'Tony'
    #The subject line
    #The body and the attachments for the mail
    message.attach(MIMEText(mail_content, 'plain'))
    attach_file_name = files
    file = open(files, 'r')
    audio = MIMEText(file.read())
    file.close()
    audio.add_header('Content-Disposition', 'attachment', filename=files)
    attach_file = open(files, 'r') # Open the file as binary mode
    payload = MIMEBase('application', 'octate-stream')
    payload.set_payload((attach_file).read())
    encoders.encode_base64(payload) #encode the attachment
    #add payload header with filename
    payload.add_header('Content-Decomposition', 'attachment', filename=attach_file_name)
    message.attach(payload)
    #Create SMTP session for sending the mail
    session = smtplib.SMTP('smtp.gmail.com', 587) #use gmail with port
    session.starttls() #enable security
    session.login(sender_address, sender_pass) #login with mail_id and password
    text = message.as_string()
    session.sendmail(sender_address, receiver_address, text)
    session.quit()
def emy(r, contents, user):
    # connect with Google's servers
    smtp_ssl_host = 'smtp.gmail.com'
    smtp_ssl_port = 465
    # use username or email to log
    username = 'cooperhaddick@gmail.com'
    password = mailpass


    from_addr = 'cooperhaddick@gmail.com'
    if r == 'mom' or 'Mom':
        a=['nahaddick@gmail.com']
    elif r == 'Sam':
        a=['sammichaelflynn@gmail.com']
    elif r=='Kelton':
        a=['kelltonholzer@gmail.com']
    elif r=='Nolan':
        a=['nolan.holzer@gmail.com']
    elif r=='me':
        a=['cooperhaddick@gmail.com']
    else:
        a=None
    to_addrs = a

    # the email lib has a lot of templates
    # for different message formats,
    # on our case we will use MIMEText
    # to send only text
    message = MIMEText(contents)
    message['subject'] = 'tony'
    message['from'] = from_addr
    message['to'] = ', '.join(to_addrs)

    # we'll connect using SSL
    server = smtplib.SMTP_SSL(smtp_ssl_host, smtp_ssl_port)
    # to interact with the server, first we log in
    # and then we send the message
    server.login(username, password)
    server.sendmail(from_addr, to_addrs, message.as_string())
    server.quit()

# Function for opening the
def files(person):
    # file explorer window
    def browseFiles(person):
        filename = filedialog.askopenfilename(initialdir = "/",
                                              title = "Select a File",
                                              filetypes = (("Text files",
                                                            "*.*"),
                                                           ("all files",
                                                            "*.*")))
         
        # Change label contents
        label_file_explorer.configure(text="File Opened: "+str(filename))
        emya(filename, person)
         
         
                                                                                                     
    # Create the root window
    window = tk.Tk()
     
    # Set window title
    window.title('File Explorer')
     
    # Set window size
    window.geometry("500x500")
     
    #Set window background color
    window.config(background = "white")
     
    # Create a File Explorer label
    label_file_explorer = tk.Label(window,
                                text = "File Explorer using Tkinter",
                                width = 100, height = 4,
                                fg = "blue")
     
         
    button_explore = tk.Button(window,
                            text = "Browse Files",
                            command = browseFiles(person))
     
    button_exit = tk.Button(window,
                         text = "Exit",
                         command = window.destroy)
     
    # Grid method is chosen for placing
    # the widgets at respective positions
    # in a table like structure by
    # specifying rows and columns
    label_file_explorer.grid(column = 1, row = 1)
     
    button_explore.grid(column = 1, row = 2)
     
    button_exit.grid(column = 1,row = 3)
     
    # Let the window wait for any events
    window.mainloop()
    return button_exit

def video(v=['171124_B1_HD_001.mp4', 'hd0933.mov', '3d_ocean_1590675653.mov']):
    c=random.choice(v)
    cap = cv2.VideoCapture(c)
       
    # Check if camera opened successfully 
    if (cap.isOpened()== False):  
      print("Error opening video  file") 
       
    # Read until video is completed 
    while(cap.isOpened()): 
          
      # Capture frame-by-frame 
      ret, frame = cap.read() 
      if ret == True: 
       
        # Display the resulting frame 
        cv2.imshow('Frame', frame) 
       
        # Press Q on keyboard to  exit 
        if cv2.waitKey(25) & 0xFF == ord('q'): 
          break
       
      # Break the loop 
      else:  
        break
       
    # When everything done, release  
    # the video capture object 
    cap.release() 
       
    # Closes all the frames 
    cv2.destroyAllWindows() 
def play(song="[' Iron Man']"):
    song= 'Music/'+song
    try:
        mixer.music.load(song)
        mixer.music.play()
    except:
        engine.say(song+'is not in my librarey')
        engine.runAndWait()

    #os.system('omxplayer ' + song)
def time2():
    now = datetime.now()
    current_time = now.strftime("%H:%M:%S")
    thetime = (current_time)
    num2 = thetime[3:-3]
    num1 = thetime[0:-6]
    dots = ':'
    num3 = int(num1)
    if num3 > 12:
        num3 -= 12
    print('The time is',num3,dots,num2)
    num3=str(num3)
    num2=str(num2)
    thing= num3+','+num2
    return thing
class Application(tk.Frame):
    def __init__(self, master=None):
        super().__init__(master)
        self.master = master
        self.pack()
        self.create_widgets()
    def create_widgets(self):
        global rate
        global speaktext
        global mailpass
        speaktext=str(speaktext)
        self.w1 = tk.Scale(self.master, from_=0, to=300, length=300, fg = 'red')
        self.w1.pack()
        self.w1.set(rate)
        self.w2 = tk.Scale(self.master, from_=0, to=100, length=300, fg = 'red')
        self.w2.pack(side='right')
        self.w2.set(100)
        self.w3=tk.Entry(self.master)
        self.w3.pack(side='top')
        self.w3.insert(0, speaktext)
        self.w4=tk.Entry(self.master)
        self.w4.pack(side='top')
        self.w5=tk.Entry(self.master)
        self.w5.pack(side='top')
        self.w5.insert(0, mailpass)
        self.quit = tk.Button(self.master, text='quit', command=self.master.destroy, fg="red").pack(side='right')
        self.ok = tk.Button(self.master, text='save', command=self.show_values, fg="green").pack(side='bottom')
    def show_values(self):
        global rate
        rate = self.w1.get()
        engine.setProperty('rate', rate)
        self.get_vol()
        self.get_text()
        self.get_mail()
        self.new_RFID()
    def get_text(self):
        global speaktext
        speaktext=self.w3.get()
        engine.say(speaktext)
        engine.runAndWait()
    def get_vol(self):
        global speaktext
        vol=self.w2.get()
        vol=vol/100
        mixer.music.set_volume(vol)
        engine.setProperty('volume', vol)
    def get_mail(self):
        global mailpass
        mailpass=self.w4.get()
    def new_RFID(self):
        reader = SimpleMFRC522()
        try:
            text = self.w5.get()
            print("Now place your tag to write")
            reader.write(text)
            print("Written")
        finally:
            GPIO.cleanup()

class pas(tk.Frame):
    def __init__(self, master=None):
        super().__init__(master)
        self.master = master
        self.master.bind('<Return>', self.show_values)
        self.pack()
        self.create_widgets()
    def create_widgets(self):
        self.w3=tk.Entry(self.master, show = '*')
        self.w3.pack(side='top')
        self.w3.focus()
    def show_values(self, r):
        global passw
        passw=self.w3.get()
        passw=passw.split('z')
        self.master.destroy()
class users(tk.Frame):
    def __init__(self, master=None):
        super().__init__(master)
        self.master = master
        self.master.bind('<Return>', self.show_values)
        self.pack()
        self.create_widgets()
    def create_widgets(self):
        self.w2=tk.Button(self.master, text= 'New', command = self.submit).pack(side='bottom')
        self.w3=tk.Entry(self.master)
        self.w3.pack(side='top')
        self.w3.focus()
    def show_values(self, r):
        global user
        user=self.w3.get()
        user=user.split('x')
        self.master.destroy()
    def submit(self):
        a=self.w3.get()
        b=open(a+'.obj', 'w')
        b.close()
        original = r'/home/pi/code/python/jarvis/settings/data.obj'
        target = r'/home/pi/code/python/jarvis/settings/'+a+'.obj'
        shutil.copyfile(original, target)
        global new
        new = True
        global newuser
        newuser = a
        self.master.destroy()
class newpass(tk.Frame):
    def __init__(self, user=None):
        global newuser
        r=newuser
        p=input("what would you like your password to be: ")
        r=str(r)
        a=open('jarvis.py', 'a')
        a.write("elif user[0] =='"+ r+"':\n")
        a.write("   root = tk.Tk()\n")
        a.write("   root= tk.Tk()\n")
        a.write("   root.title('password')\n")
        a.write("   password= pas(master=root)\n")
        a.write("   password.mainloop()\n")
        a.write("   if passw[0] =='"+p+ "':\n")
        a.write("       run(user[0])\n")
        a.write("   else:\n")
        a.write("       print ('you are not '+user[0])\n")
        a.close()
class Motor:
    def __init__(self):
        self.pwm = PCA9685(0x40, debug=True)
        self.pwm.setPWMFreq(50)
    def duty_range(self,duty1,duty2,duty3,duty4):
        if duty1>4095:
            duty1=4095
        elif duty1<-4095:
            duty1=-4095        
        
        if duty2>4095:
            duty2=4095
        elif duty2<-4095:
            duty2=-4095
            
        if duty3>4095:
            duty3=4095
        elif duty3<-4095:
            duty3=-4095
            
        if duty4>4095:
            duty4=4095
        elif duty4<-4095:
            duty4=-4095
        return duty1,duty2,duty3,duty4
        
    def left_Upper_Wheel(self,duty):
        if duty>0:
            self.pwm.setMotorPwm(0,0)
            self.pwm.setMotorPwm(1,duty)
        elif duty<0:
            self.pwm.setMotorPwm(1,0)
            self.pwm.setMotorPwm(0,abs(duty))
        else:
            self.pwm.setMotorPwm(0,4095)
            self.pwm.setMotorPwm(1,4095)
    def left_Lower_Wheel(self,duty):
        if duty>0:
            self.pwm.setMotorPwm(3,0)
            self.pwm.setMotorPwm(2,duty)
        elif duty<0:
            self.pwm.setMotorPwm(2,0)
            self.pwm.setMotorPwm(3,abs(duty))
        else:
            self.pwm.setMotorPwm(2,4095)
            self.pwm.setMotorPwm(3,4095)
    def right_Upper_Wheel(self,duty):
        if duty>0:
            self.pwm.setMotorPwm(6,0)
            self.pwm.setMotorPwm(7,duty)
        elif duty<0:
            self.pwm.setMotorPwm(7,0)
            self.pwm.setMotorPwm(6,abs(duty))
        else:
            self.pwm.setMotorPwm(6,4095)
            self.pwm.setMotorPwm(7,4095)
    def right_Lower_Wheel(self,duty):
        if duty>0:
            self.pwm.setMotorPwm(4,0)
            self.pwm.setMotorPwm(5,duty)
        elif duty<0:
            self.pwm.setMotorPwm(5,0)
            self.pwm.setMotorPwm(4,abs(duty))
        else:
            self.pwm.setMotorPwm(4,4095)
            self.pwm.setMotorPwm(5,4095)
            
 
    def setMotorModel(self,duty1,duty2,duty3,duty4):
        duty1,duty2,duty3,duty4=self.duty_range(duty1,duty2,duty3,duty4)
        self.left_Upper_Wheel(-duty1)
        self.left_Lower_Wheel(-duty2)
        self.right_Upper_Wheel(-duty3)
        self.right_Lower_Wheel(-duty4)

def login():
    L1 = 5
    L2 = 6
    L3 = 13
    L4 = 19

    C1 = 12
    C2 = 16
    C3 = 20
    C4 = 21

    log = []

    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BCM)

    GPIO.setup(L1, GPIO.OUT)
    GPIO.setup(L2, GPIO.OUT)
    GPIO.setup(L3, GPIO.OUT)
    GPIO.setup(L4, GPIO.OUT)

    GPIO.setup(C1, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
    GPIO.setup(C2, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
    GPIO.setup(C3, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
    GPIO.setup(C4, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)

    def readLine(line, characters):
        GPIO.output(line, GPIO.HIGH)
        if(GPIO.input(C1) == 1):
            print(characters[0])
            log.append(characters[0])
        if(GPIO.input(C2) == 1):
            print(characters[1])
            log.append(characters[1])
        if(GPIO.input(C3) == 1):
            print(characters[2])
            log.append(characters[2])
        if(GPIO.input(C4) == 1):
            print(characters[3])
            log.append(characters[3])
        GPIO.output(line, GPIO.LOW)

    try:
        while True:
            readLine(L1, ["1","2","3","A"])
            readLine(L2, ["4","5","6","B"])
            readLine(L3, ["7","8","9","C"])
            readLine(L4, ["*","0","#","D"])
            time.sleep(0.15)
            if str(log) in ["['1', '6', '4']"]:
                break
    except KeyboardInterrupt:
        GPIO.cleanup()

if __name__ == "__main__":
    main()
