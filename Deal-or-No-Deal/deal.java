import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class deal {
        
  
    public static void main(String[] args) throws IOException {
        JFrame jf = new JFrame();
  		MyPanel m = new MyPanel();
  	//	m.setFocusable(true);
	//	m.requestFocusInWindow();
  		jf.setSize(1366, 768);
  		m.setSize(jf.getSize());
  		jf.add(m); //adds the panel to the frame so that the picture will be drawn
      	jf.addMouseListener(m);
      	jf.addKeyListener(m);
  		jf.setVisible(true); //allows the frame to be shown.
  		m.setVisible(true); //allows the panel to be shown.
  		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // game grid is [1..M][1..N], border is used to handle boundary cases
       
    }
    
}
class MyPanel extends JPanel implements ActionListener, MouseListener, KeyListener 
{
	static Timer time;
	int code=0;
	ArrayList<Integer> placeClicked= new ArrayList<Integer>();
	public Case[][] listCases= new Case[6][5];
	public BufferedImage[][] values= new BufferedImage[13][2]; 
	public int[] valueOfCases= new int[]{1,5,10,15,25,50,75,100,200,300,400,500,750,1000,5000,10000,25000,50000,75000,100000,200000,300000,400000,500000,750000,1000000};
	private BufferedImage imageFile;
	boolean cont=true;	
	public	int currentXlistCase=-1;
	public int currentYlistCase=-1;
	public boolean offerGivenBefore=false;
	public boolean offerGivenNow=false;
	boolean[] resetBooleans= new boolean[4];
	int valueLastCase=0;
	public boolean gameFinished=false;
	public boolean gameStarted=false;
	BufferedReader f;
	PrintWriter out;
	int previousMoney=0;
	public boolean bugFix=false;
	public boolean adventureMode=false;
	int currentMoneyEarned=0;
	int color=0;
	int blink=0;
	boolean map=false;
	int level=0;
	public int[] levelGoals= new int[]{20000,50000,100000,200000,300000,450000,600000,800000,1100000,1500000,2000000,3000000};
	public String[] cities= new String[]{"Seattle","Denver","San Francisco","Los Angeles","Pheonix",
	"Dallas","Atlanta","Miami","Minneapolis","Chicago","New York City","Washington DC",};
	//public int adventureMoney=0; 
	MyPanel() throws IOException
	{
		
		 f= new BufferedReader(new FileReader("deal.in.txt"));
        out= new PrintWriter(new BufferedWriter(new FileWriter("deal.in.txt", true)));
        String previous=f.readLine();
        String current=previous;
      	while(previous!=null)
      	{	
      		current=previous;
      		previous=f.readLine();
      	}
      	if(current!=null && current.length()>0)	
        	previousMoney=Integer.parseInt(current);
        System.out.println(previousMoney);
        init();
		time = new Timer(15,this); //sets delay to 15 millis and calls the actionPerformed of this class.
		setVisible(true);
		time.start();

	}
	public void init()
	{
	//	String s="\t";
		
//		System.out.println(s.length());
		int count=0;
		for(int x=0; x<listCases.length; x++) //x max is 6
			for(int y=0; y<listCases[0].length; y++) //y max is 5
			{
				String	caseName = "case" + (count+1) + ".jpg";
				if(count<valueOfCases.length)
				listCases[x][y]=new Case(valueOfCases[count],false,count+1, readCaseFile(caseName)); 
					count++; 
			}
		count=0;
		for(int x=0; x<values[0].length; x++) //x max is 13
			for(int y=0; y<values.length; y++) //y max is 2
			{
				if(count<valueOfCases.length)
				{		
						values[y][x]= readImageFile(""+valueOfCases[count]+".PNG");
				}
						count++;
			}
			
		count=0;
		swapValues(); //randomizes case values
		


//Add into paint component method
 				
	}
	public BufferedImage readImageFile(String name)
	{
		try
        {
       // 	System.out.println(name);
            imageFile = ImageIO.read(new File(name));
            for(int x=0;x<imageFile.getWidth();x++)
            	for(int y=0;y<imageFile.getHeight();y++)
            	{
            		
            	if(imageFile.getRGB(x,y)==0xFFFFFFFF)
            			imageFile.setRGB(x,y,0xFF000000);
            	}
            return imageFile.getSubimage(2,0,230,33);
        }
        catch (IOException e)
        {
            System.out.println(e);
            return null;
            
        }
	}
	public BufferedImage readCaseFile(String name)
	{
		try
        {
     //   	System.out.println(name);
            imageFile = ImageIO.read(new File(name));
           
        }
        catch (IOException e)
        {
            System.out.println(e);
            return null;
            
        }
        return imageFile;
	}
	public BufferedImage readHowie()
	{
		try
		{
			 imageFile = ImageIO.read(new File("abc123.jpg"));

		}
		catch (IOException e)
        {
            System.out.println(e);
            return null;
            
        }
        return imageFile;
	}
	public void paintComponent(Graphics g) 
 		{
 			if(adventureMode==true && !gameStarted)
 			{
 				
 				g.setColor(new Color(color,color,color));
 				g.fillRect(0,0,1666,968);
 				map=true;
 				
 				if(color>252)
 				{
 					g.drawImage(readCaseFile("us_mapjpg.jpg"), 50 ,50, this);
 					//insert map
 					

					drawGreenCircle(g, level);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
					g.setColor(new Color(0,0,0));
 					g.drawString("Level "+(level+1), 100,600);
 					g.drawString("Going to "+cities[level],100,650);
 					if(placeClicked.size()==2)
  					{
  						gameStarted=true;
  						
  					}
 				}
 				placeClicked.clear();
 			}
 			else if(!gameStarted)
 			{
 				Graphics2D g2 = (Graphics2D) g;	
 				g2.setStroke(new BasicStroke(4));
				g.setColor(new Color(0,0,0));
 				g.fillRect(0,0,1666,968);
  				g.setColor(new Color(239,204,0));
  				g.setFont(new Font("TimesRoman", Font.PLAIN, 70));
  				g.drawString("DEAL OR NO DEAL", 300, 200);
  				g.drawImage(readHowie(),490,300,this);
  		//		g.drawString("DEAL OR NO DEAL", 300, 300);
  				g.setColor(Color.WHITE);
  				g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
  				g.drawString("Lifetime earnings: $"+addCommas(previousMoney), 430, 530);
  				g.setColor(new Color(230,230,230));
				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
				g.fillRect(200,300,250,125);
				g.fillRect(750,300,250,125);
				g.setColor(Color.GREEN);
				g2.drawRect(200,300,250,125);
			//	g.setFont(new Font("TimesRoman", Font.BOLD, 50));
				g.drawString("ORIGINAL",250, 370);
				g.setColor(Color.RED);
				g2.drawRect(750,300,250,125);
				g.drawString("ADVENTURE",760, 370);
  				if(placeClicked.size()==2)
  				{
  						int xCord= placeClicked.get(placeClicked.size()-2);
  						int yCord= placeClicked.get(placeClicked.size()-1);
  						if(xCord<=450 && xCord>=250 && yCord>=300 && yCord<=425)
  							gameStarted=true;
  						if(xCord<=1000 && xCord>=750 && yCord>=300 && yCord<=425)
  						{
  							adventureMode=true;
  						}
  					placeClicked.clear();
  				}
 			}
 			else if(!gameFinished)//makes grid
 			{	
 				g.setColor(Color.BLACK);
 				g.fillRect(0,0,1666,968);
 				for(int x=0; x<values[0].length; x++)
 				{	
 					for(int y=0; y<values.length; y++)
					{
						g.drawImage(values[y][x],x*1000+10,y*50+50,this); //display horizontally so need to change x-values first
					}
 				}
  				g.setColor(Color.WHITE);
  				g.fillRect(260,0,730,1000);
  				g.setColor(Color.BLACK);
  				int dealBox=350;
  				g.fillRoundRect(dealBox,25,550,75,100,100);
  				g.setColor(new Color(239,204,0));
  				g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
  				g.drawString("DEAL OR NO DEAL", dealBox+60, 75);
  			
  				for(int y=0; y<listCases.length; y++)
  				{
					for(int x=0; x<listCases[0].length; x++)
					{
						if(listCases[y][x]!=null && !listCases[y][x].isOpen)
						{
						//		System.out.println(x+" "+y);
							g.drawImage(listCases[y][x].cases,x*130+300,y*100+110,this);
						}
					}
  				}
  				
				if(!checkFinishedCases())
				{
					offerGivenBefore=false;
					offerGivenNow=false;
				}
				if(countFinishedCases()>20 && offerGivenNow==false && offerGivenBefore==true && resetBooleans[24-countFinishedCases()]==false)
				{
					resetBooleans[24-countFinishedCases()]=true;
					offerGivenBefore=true;
					offerGivenNow=true;
				}
				if(checkFinishedCases() && !offerGivenBefore)
				{
					offerGivenBefore=true;
					offerGivenNow=true;
				}
  			
 				if(!offerGivenNow)
 				{		
 			//	System.out.println(placeClicked.size()+"rerne");
 		     		if(placeClicked.size()==2)
  					{
  						int check=checkCase(g, placeClicked.get(placeClicked.size()-2),placeClicked.get(placeClicked.size()-1));
  					
 						if(check>0)
 						{
 							cont=false;	
 						}
 						else
 							placeClicked.clear();
  					}		
  					if(cont==false)
					{
						if(placeClicked.size()>=4)
						{
							placeClicked.clear();
							cont=true;
							listCases[currentYlistCase][currentXlistCase].finishedDisplay=true;
						}
					}	
 				}
 			
				if(checkFinishedCases() && offerGivenBefore && offerGivenNow)
				{
					//System.out.println(placeClicked.size());	   //givesOffer
					Graphics2D g2 = (Graphics2D) g;		
    				g2.setStroke(new BasicStroke(4)); 
    				g2.setColor(new Color(0,0,0));
    				g2.fillRect(320,100,600,400);
    				g.setColor(Color.YELLOW);
					g2.drawRect(322,102,596,396);
					g.setColor(new Color(230,230,230));
					g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
					int offer= computeBankOffer(average("largeNumsOnly"))+average();
					g.drawString("This is your offer: $"+addCommas(offer),500, 150);
					g.fillRect(340,250,250,125);
					g.fillRect(650,250,250,125);
					g.setColor(Color.GREEN);
					g2.drawRect(340,250,250,125);
					g.setFont(new Font("TimesRoman", Font.BOLD, 50));
					g.drawString("DEAL",390, 320);
					g.setColor(Color.RED);
					g2.drawRect(650,250,250,125);
					g.drawString("NO DEAL",660, 320);
					if(placeClicked.size()>=2)
  					{
  						int xCor= placeClicked.get(placeClicked.size()-2);
  						int yCor= placeClicked.get(placeClicked.size()-1);
  						if(xCor<=590 && xCor>=340 && yCor>=250 && yCor<=375)
  						{
  								gameFinished=true;
  								valueLastCase=offer;
  						}
  						else if(xCor<=900 && xCor>=650 && yCor>=250 && yCor<=375)
  							offerGivenNow=false;
  						placeClicked.clear();
  					}			
				}
				
				if(countFinishedCases()==24 && !offerGivenNow && offerGivenBefore)
  				{
  					if(bugFix)
  					{	
  					g.setColor(Color.BLACK);
					g.setFont(new Font("TimesRoman", Font.BOLD, 30));
  					g.drawString("PICK FINAL CASE TO KEEP",430, 660);
  					}
  					bugFix=true;
  				}
  				if(countFinishedCases()>=25)
  				{
  					gameFinished=true;
  				}
 			}
 			else
 			{
 				if(!adventureMode)
 				{
 					g.setColor(new Color(0,0,0));
    				g.fillRect(0,0,1365,767);
    				g.setColor(new Color(239,204,0));
    				g.setFont(new Font("TimesRoman", Font.PLAIN, 55));
  					g.drawString("YOU WON $"+addCommas(valueLastCase)+"!",390, 370);
  					g.drawImage(readCaseFile("gold bars small.jpg"), 50 ,30, this);
  					g.drawImage(readCaseFile("gold bars small.jpg"), 450 ,30, this);
  					g.drawImage(readCaseFile("gold bars small.jpg"), 850 ,30, this);
  					g.drawImage(readCaseFile("migos small.jpg"), 50 , 400, this);
  					g.drawImage(readCaseFile("migos small.jpg"), 350 , 400, this);
  					g.drawImage(readCaseFile("migos small.jpg"), 650 , 400, this);
  					g.drawImage(readCaseFile("migos small.jpg"), 950 , 400, this);
  					out.println(valueLastCase+previousMoney);
 					out.close();
 				}
  				else
  				{
  					if(currentMoneyEarned+valueLastCase>levelGoals[level])
  					{		
  						if(level<11)
  						{	
  							g.setColor(new Color(0,0,0));
    						g.fillRect(0,0,1365,767);
							g.setFont(new Font("TimesRoman", Font.PLAIN, 55));
    						g.setColor(new Color(239,204,0));
    						g.drawString("Current Total: $"+addCommas(currentMoneyEarned+valueLastCase), 300, 420);
  							g.drawString("YOU WON $"+addCommas(valueLastCase)+"!",300, 370);
  							g.drawImage(readCaseFile("gold bars small.jpg"), 50 ,30, this);
  							g.drawImage(readCaseFile("gold bars small.jpg"), 450 ,30, this);
  							g.drawImage(readCaseFile("gold bars small.jpg"), 850 ,30, this);
  							g.drawImage(readCaseFile("migos small.jpg"), 50 , 450, this);
  							g.drawImage(readCaseFile("migos small.jpg"), 350 , 450, this);
  							g.drawImage(readCaseFile("migos small.jpg"), 650 , 450, this);
  							g.drawImage(readCaseFile("migos small.jpg"), 950 , 450, this);
  						}
  						else
  						{
  							g.setColor(new Color(0,0,0));
    						g.fillRect(0,0,1365,767);
							g.setFont(new Font("TimesRoman", Font.PLAIN, 55));
    						g.setColor(new Color(239,204,0));
  							g.drawString("YOU WON THE GAME WITH $"+addCommas(currentMoneyEarned+valueLastCase)+"!",250, 370);
  							g.drawImage(readCaseFile("gold bars small.jpg"), 50 ,30, this);
  							g.drawImage(readCaseFile("gold bars small.jpg"), 450 ,30, this);
  							g.drawImage(readCaseFile("gold bars small.jpg"), 850 ,30, this);
  							g.drawImage(readCaseFile("migos small.jpg"), 50 , 450, this);
  							g.drawImage(readCaseFile("migos small.jpg"), 350 , 450, this);
  							g.drawImage(readCaseFile("migos small.jpg"), 650 , 450, this);
  							g.drawImage(readCaseFile("migos small.jpg"), 950 , 450, this);
  							if(placeClicked.size()>=2)
  							{	
  							
  								out.println(currentMoneyEarned+valueLastCase+previousMoney);
  								out.close();
  								System.exit(0);
  							}
  						}
  						if(placeClicked.size()>=2)
  						{	currentMoneyEarned+=valueLastCase;
  							resetValues();
  							placeClicked.clear();
  						}
  				}
  					else if(placeClicked.size()>=2 && currentMoneyEarned+valueLastCase<levelGoals[level])
  					{
  							g.setColor(new Color(0,0,0));
    						g.fillRect(0,0,1365,767);
  							g.setColor(Color.WHITE);
							g.setFont(new Font("TimesRoman", Font.PLAIN, 55));
							g.drawString("Current Total: $"+addCommas(currentMoneyEarned+valueLastCase), 100, 300);
  							g.drawString("Sorry! You did not meet your goal of $"+addCommas(levelGoals[level]), 100, 400);
  							out.println(currentMoneyEarned+valueLastCase+previousMoney);
  							out.close();
  							if(placeClicked.size()>=4)
  								System.exit(0);
  				
  					}
  				}
 			}
 		
  			 if(adventureMode && code==51)
  			 {
  			 	Graphics2D g2 = (Graphics2D) g;		
    				g2.setStroke(new BasicStroke(4)); 
    				g2.setColor(new Color(0,0,0));
    				g2.fillRect(320,100,600,400);
    				g.setColor(Color.YELLOW);
					g2.drawRect(322,102,596,396);
					g.setColor(new Color(230,230,230));
					g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
					g.drawString("Current Money: $"+addCommas(currentMoneyEarned),400, 150);
				//	System.out.println();
					g.drawString("Money Deficit/Surplus: $"+addCommas(currentMoneyEarned-levelGoals[level]),400, 200);
  			 }
  			
 		}
 		
 		
 	public void drawGreenCircle(Graphics g, int level)
 	{
 		int graphX=17;
		int graphY=40;
		g.setColor(new Color(59,154,59));
		if((blink/10)%2==0)
		{
			if(level ==0)
			g.fillOval(195-graphX, 123-graphY, 20,20); //198 123 Seattle 1
			else if(level == 1)
			g.fillOval(372-graphX, 272-graphY, 20,20);	//372 272 Denver 2
			else if(level == 2)	
			g.fillOval(155-graphX, 258-graphY, 20,20);   //156 257 San 3   
			else if(level == 3)  
			g.fillOval(191-graphX, 333-graphY, 20,20); //194 335 Los 4
			else if(level == 4)
			g.fillOval(266-graphX, 358-graphY, 20,20); //267 357 Pheonix 5
			else if(level == 5)
			g.fillOval(457-graphX, 385-graphY, 20,20); //457 385 Dallas 6 
			else if(level == 6)
			g.fillOval(649-graphX, 368-graphY, 20,20); //650 366 Atlanta 7
			else if(level == 7)
			g.fillOval(724-graphX, 489-graphY, 20,20);  //724 489 Miami 8
			else if(level == 8)
			g.fillOval(515-graphX, 180-graphY, 20,20);   //516 180 Minneapolis 9
			else if(level == 9)
			g.fillOval(584-graphX, 241-graphY, 20,20);   //584 241 Chicago 10
			else if(level == 10)
			g.fillOval(761-graphX, 222-graphY, 20,20);   //761 222 New York City 11
			else if(level == 11)
			g.fillOval(722-graphX, 266-graphY, 20,20);   //724 264 Washington DC 12
		}
 	}
 	
 	public void swapValues()
 	{
 		for(int x=0; x<4000; x++)
 		{
 			int random= (int)(Math.random()*6);
 			int random2=(int) (Math.random()*5);
 			int swap= (int)(Math.random()*6);
 			int swap2=(int) (Math.random()*5);
 			if(listCases[random][random2]!=null && listCases[swap][swap2]!=null)
 			{
 				int temp=listCases[random][random2].value;
 				listCases[random][random2].value=listCases[swap][swap2].value;
 				listCases[swap][swap2].value=temp;
 			}
 			else
 			{
 				x--;
 			}
 		}
 	}
 	public double[] average(String s)
	{

		Double average =0.0;
		Double total =0.0;
		Double number =0.0;
		for(int y =0; y < listCases.length; y++)
		{
			for(int x =0;x < listCases[0].length;x++)
			{
				if(listCases[y][x] != null && listCases[y][x].getValue() >= 100000 && !listCases[y][x].finishedDisplay)
				{
				//	System.out.println("big= "+listCases[y][x].getValue());
					total+=listCases[y][x].getValue();
					
				}
				if(listCases[y][x] != null && listCases[y][x].getValue() > 0 && !listCases[y][x].finishedDisplay)
				{
					number++;
				}
			}

		}
	

		average = total/number;
	//	System.out.println("bigAV= "+average);
		return new double[]{average, number};
	}
	
	public int average()
	{

		Double average =0.0;
		Double total =0.0;
		Double number =0.0;
		for(int y =0; y < listCases.length; y++)
		{
			for(int x =0;x < listCases[0].length;x++)
			{
				if(listCases[y][x] != null && listCases[y][x].getValue() < 100000 && !listCases[y][x].finishedDisplay)
				{
					total+=listCases[y][x].getValue();
					number++;
				}
			}

		}
	

		average = total/number;
		return (int) (average*1.15);
	}
	
	public int computeBankOffer(double[] d)
	{
		double amountTaken= 26-d[1];
		double temp= d[0]*(.12*amountTaken*amountTaken+17)/100; //final= average*(cases removed)^2*.12+20 gives a quadratic range between 20-100
		return (int) temp;
		
	}
	
	public int checkCase(Graphics g, int xCor, int yCor)
	{
		for(int y=0; y<listCases.length; y++)
  			{
				for(int x=0; x<listCases[0].length; x++)
				{
					if(xCor >= x*130+310 && xCor <= (x+1)*130+295 && yCor >= y*100+155 && yCor <= (y+1)*100+135 && listCases[y][x]!=null && !listCases[y][x].finishedDisplay)
					{
						if(!listCases[y][x].isOpen)
						{
							currentXlistCase=x;
							currentYlistCase=y;
						}
							valueLastCase=listCases[y][x].value;
							dimMoneyValue(listCases[y][x].value);
							displayCase(g, listCases[y][x].value);
							listCases[y][x].open();
							
						
						return listCases[y][x].order;
					}
				}
  			}
		return 0;
	} 
		
	public int countFinishedCases()
	{
		int count =0;
		for(int y=0; y<listCases.length; y++)
  			{
				for(int x=0; x<listCases[0].length; x++)
				{
					if(listCases[y][x]!=null && listCases[y][x].finishedDisplay)
					{
						count++;
					}
				}
  			}
  			return count;
	}
		
	public void displayCase(Graphics g, int val)
	{	
		Graphics2D g2 = (Graphics2D) g;
    	g2.setStroke(new BasicStroke(4)); 
    	g2.setColor(new Color(0,0,0));
    	g2.fillRect(320,100,600,400);
    	g.setColor(Color.YELLOW);
		g.drawRect(322,102,596,396);
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		g.drawString("$"+addCommas(val),540, 300);
		
	}
	
	public void dimMoneyValue(int value) 
	{
		int a=-1;
		for( int i=0; i< valueOfCases.length ; i ++ )
    		if( valueOfCases[ i ] == value)
    		{a= i; break;}
		int r=a;
		int c=0;
		if(a>=13)
		{
			r-=13;
			c++;
		}
	
		BufferedImage imageFile=values[r][c];
  				for(int x=0;x<imageFile.getWidth();x++)
            		for(int y=0;y<imageFile.getHeight();y++)
            		{
  						int temp= imageFile.getRGB(x,y);
            			String n=Integer.toBinaryString(temp);
            			String n2=n.substring(n.length()-24); //gets value of color excluding RGB value
            			n="00111000"+n2;
            			int nColor=Integer.parseInt(n,2);
            			imageFile.setRGB(x,y,nColor);
            		}
            	values[r][c]=imageFile;
	}

	public boolean checkFinishedCases()
	{
		int count=  countFinishedCases();
	//	System.out.println(count);
		if(count==6 || count==11 || count==15 || count==18 || (count>=20 && count<=24))
			return true;
			return false;
	}
	
	 public String addCommas(int value)
	{
		boolean negative=false;
		if(value<0)
		{
			value*=-1;
			negative=true;
		}
		if(value==0)
			return value+"";
		String out = "";
		String val = Integer.toString(value);
		String val1 = val;
		String val2 = val;
		if(value/1000000>=1000)
		{
			String right = val.substring(val.length()-3);
			String mid = val.substring(val.length()-6,val.length()-3);
			String top= val.substring(val.length()-9,val.length()-6);
			val1=val1.substring(0,val.length()-9);
			out=val1+","+top+","+mid+","+right;
		}
		else if(value/1000000 >= 1)
		{
			String right = val.substring(val.length()-3);
			String mid = val.substring(val.length()-6,val.length()-3);
			val1=val1.substring(0,val.length()-6);
			out=val1+","+mid+","+right;
		}
		else if(value/1000 >=1)
		{
			String right = val.substring(val.length()-3);
			val1 = val1.substring(0,val.length()-3);	
			out = val1 + "," + right;
		}
		else
			out=val;
		if(negative)
			out="-"+out;
		return out;
	}
	
	public void resetValues()
	{
		if(valueLastCase!=0)
		{
			valueLastCase=0;
			level++;
			resetBooleans[0]=false; resetBooleans[1]=false; resetBooleans[2]=false; resetBooleans[3]=false;
			currentXlistCase=-1;
			currentYlistCase=-1;
			offerGivenBefore=false;
			offerGivenNow=false;
			gameFinished=false;
			gameStarted=false;
			bugFix=false;
			color=0;
			blink=0;
			map=false;
			init();
	/*		for(int x=0; x<listCases.length; x++) //x max is 6
				for(int y=0; y<listCases[0].length; y++) //y max is 5
				{
					if(listCases[x][y]!=null)
					{	
					listCases[x][y].finishedDisplay=false;
					listCases[x][y].isOpen=false;
					restoreMoneyValues(listCases[x][y].value);
					}
				} */
		}
	}

 	public void actionPerformed(ActionEvent e)
	{
		if(adventureMode && color<253)
			color+=3;
		if(map)
			blink++;
		repaint();
	}
	public void mouseClicked(MouseEvent e)
	{
		int first= e.getX();
		int second= e.getY();
		placeClicked.add(first);
		placeClicked.add(second);
		System.out.println(placeClicked.get(0)+" "+placeClicked.get(1));
	}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void keyPressed(KeyEvent e)
	{
    	code = e.getKeyCode();

	}

	public void keyReleased(KeyEvent e) {}
 	public void keyTyped(KeyEvent e){}

}

class Case
{
	public Boolean isOpen;
	public Integer value;
	public Integer order;
	public BufferedImage cases;
	public Boolean finishedDisplay=false;



	public Case(Integer v, Boolean op, int or, BufferedImage c)
	{

	isOpen = op;
	value = v;
	order = or;
	cases = c;

	}
	public void open()
	{
		isOpen = true;
	}

	public boolean getOpen()
	{
		return isOpen;
	}

	public Integer getValue()
	{
		return value;
	}

	public Integer getOrder()
	{
		return order;
	}
}

//198 123 Seattle
//372 272 Denver 
//156 257 San
//194 335 Los
//367 357 Pheonix
//457 385 Dallas
//650 366 Atlanta
//724 489 Miami
//584 241 Chicago
//516 180 Minneapolis
//724 264 Washington DC
//761 222 New York City
 
//131478.08
/*for(int x=0;x<imageFile.getWidth();x++)
            	for(int y=0;y<imageFile.getHeight();y++)
            	{
            		
            	int temp= imageFile.getRGB(x,y);
            	String n=Integer.toBinaryString(temp);
            	String n1=n.substring(0,8);
            	String n2=n.substring(8);
            	n="00110000"+n2;
         //   System.out.println(n);
            	int nColor=Integer.parseInt(n,2);
            			imageFile.setRGB(x,y,nColor);
            	}
            return imageFile.getSubimage(2,0,230,33); */
            
            
/* 	public void restoreMoneyValues(int value) 
	{
		int a=-1;
		for( int i=0; i< valueOfCases.length ; i ++ )
    		if( valueOfCases[ i ] == value)
    		{a= i; break;}
		int r=a;
		int c=0;
		if(a>=13)
		{
			r-=13;
			c++;
		}
	
		BufferedImage imageFile=values[r][c];
  				for(int x=0;x<imageFile.getWidth();x++)
            		for(int y=0;y<imageFile.getHeight();y++)
            		{
  						int temp= imageFile.getRGB(x,y);
            			String n=Integer.toBinaryString(temp);
            			String n2=n.substring(n.length()-25); //gets value of color excluding RGB value
            			n=Integer.toBinaryString(250)+n2;
            			int nColor=Integer.parseInt(n,2);
            			imageFile.setRGB(x,y,nColor);
            		}
            	values[r][c]=imageFile;
	}
	*/