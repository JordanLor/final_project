package com.project.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class MessageContainer {
	
	public static int NONE = 0;
	public static int GREYSCALE = 1;
	public static int SEPIA = 2;
	
	public Bitmap messageImage;
	
	//If string = "" then no text, handle that on receiving end
	public String messageText;
	
	//We can use an integer to define which filter should be applied and decipher and apply the filter on the
	//receiving end.
	//0 = None
	//1 = Greyscale
	//2 = Sepia
	public int messageFilter;

	//Audio file will be sent with the message so we just need to tell the recipient to invoke a 
	//media player to play the message and display appropriate controls
	public boolean messageAudio;
	
	
	//If we're using this something aint right...
	public MessageContainer() {
		
	}
	
	public MessageContainer(Bitmap messageImage, String messageText, int messageFilter, boolean messageAudio) {
		this.messageImage = messageImage;
		this.messageText = messageText;
		this.messageFilter = messageFilter;
		this.messageAudio = messageAudio;
	}
	
	
	
	
	
}
