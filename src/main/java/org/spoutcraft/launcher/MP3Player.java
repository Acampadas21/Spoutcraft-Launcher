package org.spoutcraft.launcher;

import javazoom.jl.player.Player;
import java.io.InputStream;
public class MP3Player {

 private Player player;
 private InputStream is;
 
 /** Creates a new instance of MP3Player */
 public MP3Player(InputStream mp3file) 
 {
  try
  {
   // Create an InputStream to the file
   is = mp3file;
  }
  catch( Exception e )
  {
   e.printStackTrace();
  }
 }
 
 public void play()
 {
  try
  {
   player = new Player( is );
   PlayerThread pt = new PlayerThread();
   pt.start();
  }
  catch( Exception e )
  {
   e.printStackTrace();
  }
 }
 
 class PlayerThread extends Thread
 {
  public void run()
  {
   try
   {
    player.play();
   }
   catch( Exception e )
   {
    e.printStackTrace();
   }
  }
 }
}