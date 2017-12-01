package my_abstract;
abstract class Player{
	boolean pause;
	int currentPos;
	int volume;
	Player()
	{
		pause = false;
		currentPos = 0;
	}
	abstract void play(int pos);
	abstract void stop();
	
	void play()
	{
		play(currentPos);
	}
	void pause()
	{
		if(pause)
		{
			pause =false;
			play(currentPos);
		}
		else
		{
			pause = true;
			stop();
		}
	}
	void dec_volume(int dec)
	{
		volume -= dec;
	}
	void inc_volume(int inc)
	{
		volume+=inc;
	}
}
class MP3Player extends Player{

	@Override
	void play(int pos) {
		// TODO Auto-generated method stub
		System.out.println(this.getClass() + "[playing MP3]:: " + "pos : " + pos);
	}

	@Override
	void stop() {
		// TODO Auto-generated method stub
		System.out.println(this.getClass() + "[stopped MP3]:: " );
	}
	
}
class DVDPlayer extends Player{

	int currentIdx;
	@Override
	void play(int pos) {
		// TODO Auto-generated method stub
		System.out.println(this.getClass() + "[playing DVD]:: " + "pos : " + pos);
	}
	@Override
	void stop() {
		// TODO Auto-generated method stub
		System.out.println(this.getClass() + "[stopped DVD]:: " );
	}
	void nextDVD()
	{
		System.out.println(this.getClass() + ":: moved to nextTrack"+ (currentIdx+1) );
		currentIdx++;
	}
	void preDVD()
	{
		if(currentIdx>1)
		{
			System.out.println(this.getClass() + ":: moved to preTrack"+ (currentIdx-1) );
			currentIdx--;
		}
	}
	
}
class CDPlayer extends Player{
	int currentTrack;
	void play(int pos)
	{
		System.out.println(this.getClass() + "[playing CD]:: " + "pos : " + pos);
	}
	void stop()
	{
		System.out.println(this.getClass() + "[stopped CD]:: " );
	}
	void nextTrack()
	{
		System.out.println(this.getClass() + ":: moved to nextTrack"+ (currentTrack+1) );
		currentTrack++;
	}
	void preTrack()
	{
		if(currentTrack>1)
		{
			System.out.println(this.getClass() + ":: moved to preTrack"+ (currentTrack-1) );
			currentTrack--;
		}
	}
}
public class main_process {
	
	Player[] parr;
	public main_process()
	{
		parr = new Player[3];
		parr[0] = new CDPlayer();
		parr[1] = new DVDPlayer();
		parr[2] = new MP3Player();
		
		for(int i=0;i<3;i+=1)
		{
			parr[i].play();
		}
	}
	public static void main(String[] args)
	{
		new main_process();
	}
	
	
}
