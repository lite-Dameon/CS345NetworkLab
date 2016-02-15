// in second assigment 
// lambda is taken as packet sending rate  for non-bursty
// lambda is taken as burst Interval for bursty

import java.util.*;
import java.util.Comparator;

public class assignnet {
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
   		int numTest =10;
   		// Q2
   		// int numTest =1 ;
		for(int i=0; i<numTest;i++){
    		new Solution(scan);
    		//System.out.println(".............................................................................................................................................................................................................................");
		}
    	
    }
}

class Solution{
	Scanner scan;
	Queue<Event> events = new PriorityQueue<Event>(new EventComparater());
    ArrayList<Source> sources = new ArrayList<Source>();
    int packetNum = 0;
   	Switch switch_1;
    double queuingDelay =0 ;
    Sink sink;
	int succesfullyReceived = 0;
	
	double calculateAvgSizeOfAllQueue(){
		double size = 0;
		for(Source s : sources){
			size += s.queue.size();
		}
		return size/Info.NUMBER_OF_SOURCES;
	}
	
	double question3(){
		int drop =0;
		for(Source s : sources){
			drop += s.packetDropped;
		}
		drop += switch_1.pcktDropped;
		return drop/Info.MAX_RUN_TIME;
	}
	
	double question4(){
		int success=0;
		for(int i=0; i<Info.NUMBER_OF_SOURCES;i++){
			//System.out.println("Packet Received at sink"+ i+" ->  " + sink.packetRecieved[i]);
			success += sink.packetRecieved[i] ;
		}
		
		return success/Info.MAX_RUN_TIME;
	}
	
	Solution(Scanner scan){
		this.scan = scan;
		//System.out.println("Inputing ");
		input();
		//System.out.println("Handling started");
		while(events.size() != 0){
			//System.out.println("Handling ");
			
			handleEvent();
			
		}
		//System.out.println("Handling"+" done");
		
		//System.out.println("Queueing Delay" + queuingDelay);
		
		//System.out.println("Packet Num " + packetNum);	
		
		for(int i=0; i<Info.NUMBER_OF_SOURCES;i++){
			//System.out.println("Packet Received at sink"+ i+" ->  " + sink.packetRecieved[i]);
			succesfullyReceived += sink.packetRecieved[i] ;
		}
		//System.out.println("");
		
		for(Source s : sources){
			//System.out.println("Source packet Drooped "+ s.id +" -> "+ s.packetDropped);
		}
		
		if(switch_1.mode == Info.PACKET_SWITCHING){
			//System.out.println("Switch packet dropped " + switch_1.pcktDropped);
			;}
		else
		{
			for(int i=0; i<Info.NUMBER_OF_SOURCES;i++){
				//System.out.println("Switch packet dropped " +(i) +" -> "+ switch_1.packetDropped[i]);
			}
		}
		
		//Q4a)
		// System.out.println(Info.BANDWIDTH_SWITCH_TO_SINK+" "+ question4());
		
		//Q4b low_packet 
		if(switch_1.mode == Info.PACKET_SWITCHING){
				System.out.println(sources.get(0).packetSendingRate+" "+ question4());

		}
		else{
			System.out.println(sources.get(0).packetSendingRate+" "+ question4());

		}
		//Q4c  
		// System.out.println(Info.NUMBER_OF_SOURCES +" "+question4());
		//Q3 
				// System.out.println(sources.get(0).maxSize +" "+ question3());
		
		
		//Q1
		
//		//1a queue size
		// System.out.println("Average Queueing Delay / Queue Size");
		// System.out.println(switch_1.maxSize +" "+ (queuingDelay/succesfullyReceived));
//		
//		// 1b packet sending rate
//		//System.out.println("Average Queueing Delay / Packet Sending rate");
		// System.out.println(sources.get(0).packetSendingRate +" "+(queuingDelay/succesfullyReceived));
//		
		 //1c burst size
		//System.out.println("Average Queueing Delay / Bust Size");
		// System.out.println(sources.get(0).burstSize +" "+(queuingDelay/succesfullyReceived));
				
//		// 1d burst interval
//		//System.out.println("Average Queueing Delay / Burst Interval");
		// System.out.println(sources.get(0).intervalBtwBurst +" "+(queuingDelay/succesfullyReceived));
				
		//System.out.println("Successfully transferred " + (succesfullyReceived));
	
	
	}

	double getNextGenerationTime(int sid){
		Source s  = sources.get(sid);
		double time;
		int lambda = 0;
		if(s.bursty){
			lambda = s.intervalBtwBurst; 		
		}
		else{
			lambda = s.packetSendingRate;
		}
		return -1 * (Math.log(1- Math.random()) * 1.0 / lambda);
	}

	void input(){
		Info.NUMBER_OF_SOURCES = scan.nextInt();
		sink = new Sink();
		switch_1 = new Switch();
    	for(int i=0; i<Info.NUMBER_OF_SOURCES;i++)
    	{
    		Source s = new Source();
    		//details about all sources and creation of source objects

    		// id
    			s.id = i;
    		
    		//maxSize
    			s.maxSize = scan.nextInt();
    		//bursty yes or not
    			s.bursty = scan.nextBoolean();
    		// if bursty then burstSize and interval between burst
    			if(s.bursty){
    				s.burstSize = scan.nextInt();
    				s.intervalBtwBurst= scan.nextInt();
    			}
    			else
    				// packet sending rate
    				s.packetSendingRate = scan.nextInt();
    		// start time  
    			s.time = scan.nextDouble();

    			sources.add(s);

    		// event corresponding to time of type
    			Event e = new Event();
    			e.id = Info.EVENT_PACKET_GENERATION;
    			e.sourceId = s.id;
    			e.time = s.time;
    			events.add(e);

    	//System.out.println("source "+(i+1) + " done");
    	}

		    	
    	//switch details
    	switch_1.maxSize = scan.nextInt();
    	//switch maxSize
    	
    	boolean modeSwitch = scan.nextBoolean();
    	if(modeSwitch)
    		switch_1.mode = Info.CIRCUIT_SWITCHING;
    	else
    		switch_1.mode = Info.PACKET_SWITCHING;
    	// switch mode
    	
    	switch_1.service_rate = scan.nextInt();
    	//service rate

    	switch_1.SwitchUpdate();
    	//System.out.println("switch "+ " done");
    	if(switch_1.mode == Info.CIRCUIT_SWITCHING){
    		Event e = new Event();
    		e.id = Info.EVENT_PACKET_SERVICE;
    		//System.out.println("Circuit Switching");
    		e.sourceId = 0;
    		e.time = 0;
    		events.add(e);

    	}

    	// take input bandwidth from source to switch
    	Info.BANDWIDTH_SOURCE_TO_SWITCH = scan.nextInt();
    	//System.out.println("bandwidth source to swtich done");
    	//take input bandwith from switch to sink
    	Info.BANDWIDTH_SWITCH_TO_SINK = scan.nextInt();
    	//System.out.println("bandwidth swtich to sink done");
    	// take input packet size
    	Info.PACKET_SIZE = scan.nextInt();
    	//System.out.println("packet size done");
    	// take inuut the max end time
    	Info.MAX_RUN_TIME= scan.nextDouble();
    	
		//System.out.println("input "+" done -> size -> "+ events.size());
	}

	void handleEvent(){
		// take first event by .poll()
		Event e = events.poll();
		//System.out.println("queue size -> "+ events.size());
		//System.out.println("event time -> "+ e.time);
		//System.out.println("event id -> "+ e.id);
		
		

		//Q2 a)
		// System.out.println(e.time +" " + calculateAvgSizeOfAllQueue());
	
		
		if(events.size() == 0 && e.id == Info.EVENT_PACKET_SERVICE){
			return;
		}
		if(e.id == Info.EVENT_PACKET_GENERATION){
			
			//System.out.println("PACK GENERATION");
			int sid = e.sourceId;
			Source source = sources.get(sid);
			//System.out.println("Source Id->"+sid);
			if(!source.bursty)
			{
				packetNum++;
				if(source.maxSize > source.queue.size()){
					// packet generation
					Packet packet = new Packet();
					packet.sourceId = sid;
					packet.timeStamp = e.time;

					// packet added to queue
					sources.get(sid).queue.add(packet);
					//System.out.println("PACKET GENERATION SUCCESSFUL");

					//event 2
					Event e2  = new Event();
					e2.id = Info.EVENT_PACKET_TRANSMISSION_SWITCH;
					e2.sourceId = sid;
					e2.time = e.time + (Info.PACKET_SIZE*1.0)/(Info.BANDWIDTH_SOURCE_TO_SWITCH);
					//System.out.println("ratio "+(Info.PACKET_SIZE*1.0)/(Info.BANDWIDTH_SOURCE_TO_SWITCH));
					events.add(e2);

				}
				else{
					// packet dropped if non bursty source
					source.packetDropped++;
					//System.out.println("PACKET DROPPED ");
				}
				
				//event 1
				Event e1 = new Event();
				e1.id = Info.EVENT_PACKET_GENERATION;
				e1.sourceId = sid;
				e1.time = e.time + getNextGenerationTime(sid);
				//e1.time = e.time + 1.0/source.packetSendingRate;
				//System.out.println("ratio " + 1.0/source.packetSendingRate);
				if(e1.time < Info.MAX_RUN_TIME)
					events.add(e1);
					
				
			}
			else{
				for(int i = 0; i < source.burstSize;i++){
					packetNum++;
					if(source.maxSize > source.queue.size()){
						// packet generation
						Packet packet = new Packet();
						packet.sourceId = sid;
						packet.timeStamp = e.time;

						//add generated packet to source
						sources.get(sid).queue.add(packet);

						//event 2
						Event e2  = new Event();
						e2.id = Info.EVENT_PACKET_TRANSMISSION_SWITCH;
						e2.sourceId = sid;
						e2.time = e.time + (source.queue.size())*(Info.PACKET_SIZE*1.0)/(Info.BANDWIDTH_SOURCE_TO_SWITCH);
						events.add(e2);
					}
					else{
						//packet dropped in case of bursty at source
						source.packetDropped++;
						//System.out.println("BUrSTy PACKET DROPPED ");
					}
				}
				//event 1
				Event e1 = new Event();
				e1.id = Info.EVENT_PACKET_GENERATION;
				e1.sourceId = sid;
				e1.time = e.time + getNextGenerationTime(sid);
				if(e1.time < Info.MAX_RUN_TIME)
					events.add(e1);
			}
		}
		else if(e.id == Info.EVENT_PACKET_TRANSMISSION_SWITCH){

			//System.out.println("PACKET TRANSMISSION SWITCH");
			int sid = e.sourceId;
			//System.out.println("Source Id->"+sid);
			Source source = sources.get(sid);
			if(switch_1.mode == Info.PACKET_SWITCHING){
				// transfer first packet from source packet queue to switch queue
				Packet frontPacket = (source.queue).get(0);
				if((source.queue).size() > 0){
					//System.out.println("Packet removing " + (sources.get(sid)).queue.size());
					(source.queue).removeFirst();
					//System.out.println("Packet removing " + sources.get(sid).queue.size());
					
				}
				// there must be space in router queue
				if(switch_1.maxSize > switch_1.qPack.size())
				{
					switch_1.qPack.add(frontPacket);
					//event 3 with currnet event time + (length +1)*1/s
					Event e3 = new Event();
					e3.id = Info.EVENT_PACKET_SERVICE;
					e3.sourceId = sid;
					e3.time = e.time + (switch_1.qPack.size())* (1.0/switch_1.service_rate);
					events.add(e3);
					queuingDelay += (switch_1.qPack.size()-1)* (1.0/switch_1.service_rate);
				}
				else{
					//packet dropped at router packet switching mode
					switch_1.pcktDropped++;
				}
			}
			else if(switch_1.mode == Info.CIRCUIT_SWITCHING){
				
				Packet frontPacket = (source.queue).get(0);
				if(sources.get(sid).queue.size() > 0)
					(sources.get(sid).queue).removeFirst();
				if(switch_1.maxSize > switch_1.qCir.get(sid).size()){
					switch_1.qCir.get(sid).add(frontPacket);			
				}
				else{
					//packet dropped at router circuit switching mode
					switch_1.packetDropped[sid]++;
				}
			}
		}
		else if(e.id == Info.EVENT_PACKET_SERVICE){
			//System.out.println("PACKET SERVICE");
			if(switch_1.mode == Info.PACKET_SWITCHING){
				Event e4 = new Event();
				int sid = e.sourceId;	
				//System.out.println("Source Id->"+sid);
				// packet in front serviced
				if(switch_1.qPack.size() > 0)
					switch_1.qPack.removeFirst();
				e4.id = Info.EVENT_PACKET_TRANSMISSION_SINK;
				e4.sourceId = sid;
				e4.time = e.time + (Info.PACKET_SIZE*1.0)/(Info.BANDWIDTH_SWITCH_TO_SINK);
				events.add(e4);
			}
			else if(switch_1.mode == Info.CIRCUIT_SWITCHING){
				//System.out.println("Circuit switching going on");
				Event e3 = new Event();
				int sid = e.sourceId;
				e3.id = Info.EVENT_PACKET_SERVICE;
				// remove first packet
				//System.out.println("switch 1 id of source queue "+switch_1.qCir.get(sid));
				
				if(switch_1.qCir.get(sid).size() > 0){
					switch_1.qCir.get(sid).removeFirst();
					Event e4 = new Event();	
					e4.id = Info.EVENT_PACKET_TRANSMISSION_SINK;
					e4.sourceId = sid;
					e4.time = e.time + (Info.PACKET_SIZE*1.0)/(Info.BANDWIDTH_SWITCH_TO_SINK);
					events.add(e4);
				}
				
				e3.sourceId = (sid + 1) % Info.NUMBER_OF_SOURCES; 
			  	e3.time = e.time + (1.0/switch_1.service_rate);
				events.add(e3);
				queuingDelay += 1.0/switch_1.service_rate;
				
			}
		}
		else if(e.id == Info.EVENT_PACKET_TRANSMISSION_SINK){
			//System.out.println("PACKET TRANSFERRED");
				//packet successfully transferred
				int sid = e.sourceId;
				//System.out.println("Source Id->"+sid);
				sink.packetRecieved[sid]++;

		}
		
	}
}
class Source{
	int id;
	int packetSendingRate;
	int maxSize;
	int state;
	// if bursty
	boolean bursty;
	int burstSize = -1;
	int intervalBtwBurst = -1;
	LinkedList<Packet>queue = new LinkedList<Packet>();
	int packetDropped=0;
	double time;

	Source(){

	}

	Source(int packetSendingRate, int id){
		this.id = id;
		this.packetSendingRate = packetSendingRate;
	}

	Source(int packetSendingRate, int burstSize, int intervalBtwBurst, int id){
		this.id = id;
		this.packetSendingRate = packetSendingRate;
		this.burstSize= burstSize;
		this.intervalBtwBurst = intervalBtwBurst;
	}
}

class Switch{
	int maxSize;
	int mode;
	int [] packetDropped;
	int pcktDropped=0;
	int service_rate;
	ArrayList< LinkedList<Packet> > qCir = new ArrayList< LinkedList<Packet> >();
	LinkedList<Packet> qPack = new LinkedList<Packet>();
	
	Switch(){
		packetDropped = new int[Info.NUMBER_OF_SOURCES];
	}

	void SwitchUpdate(){
		if(mode == Info.CIRCUIT_SWITCHING)
			for(int i =0;i< Info.NUMBER_OF_SOURCES;i++)
				qCir.add(new LinkedList<Packet>());
		else if(mode == Info.PACKET_SWITCHING){
			//mode is PACKET_SWITCING so only one queue will be used
		}
	}
}

class Packet{
	int sourceId;
	double timeStamp;

	Packet(){

	}
	Packet(int sourceId){
		this.sourceId = sourceId;
	}

	void setCurrentTimeStamp(double time){
		this.timeStamp = time;
	}

}
/*
	event id and corresponding action
	1- packet generation
	2- transfer of packet to switch
	3- service of packet
	4- transfer from switch to sink
*/
class Sink{
	int [] packetRecieved;
	Sink(){
		packetRecieved = new int[Info.NUMBER_OF_SOURCES];
	}
}
class Event{
	double time;
	int id;
	int sourceId;
	int packetId;

	Event(){
	}
}

class EventComparater implements Comparator<Event>{
	public int compare(Event e1, Event e2){
		if(e1.time < e2.time)
			return -1;
		else if(e1.time > e2.time)
			return 1;
		return 0;
	}
}

class Info{
	static int TRANSMISSION_DELAY = 0;
	static int NUMBER_OF_SOURCES =0  ;
	static int CIRCUIT_SWITCHING = 1;
	static int PACKET_SWITCHING = 2;
	static int SOURCE_INACTIVE = -1;
	static int SOURCE_ACTIVE = 1;
	static int PACKET_SIZE=0;
	static int BANDWIDTH_SOURCE_TO_SWITCH;
	static int BANDWIDTH_SWITCH_TO_SINK;
	static int EVENT_PACKET_GENERATION = 1;
	static int EVENT_PACKET_TRANSMISSION_SWITCH = 2;
	static int EVENT_PACKET_SERVICE = 3;
	static int EVENT_PACKET_TRANSMISSION_SINK = 4;
	static double MAX_RUN_TIME = 1.2;
}
