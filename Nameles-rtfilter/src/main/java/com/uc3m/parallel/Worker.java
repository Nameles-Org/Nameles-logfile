package com.uc3m.parallel;

import java.util.List;

import org.zeromq.ZMQ;

import com.uc3m.nameles.NamelessInMemoryCache;
import com.uc3m.nameles.NamelessInMemoryCacheRef;

public class Worker {
	public static void main(String[] args) throws Exception {
		System.gc();
		NamelessInMemoryCache<String, List<Integer>> cacheRef = NamelessInMemoryCacheRef.cache("161219");
		ZMQ.Context context = ZMQ.context(1);

		// Socket to receive messages on
		ZMQ.Socket receiver = context.socket(ZMQ.PULL);
		receiver.connect("tcp://192.168.1.14:5557");
		receiver.setHWM(2);

		// Socket to send messages to
		ZMQ.Socket sender = context.socket(ZMQ.PUSH);
		sender.connect("tcp://192.168.1.14:5558");
		sender.setSendTimeOut(10000);
		sender.setLinger(0);
		// Socket to send messages to
		ZMQ.Socket sender2 = context.socket(ZMQ.PUSH);
		sender2.connect("tcp://localhost:5559");
		sender2.setSendTimeOut(10000);
		sender2.setLinger(0);

		System.gc();

		// Process tasks forever
		while (!Thread.currentThread().isInterrupted()) {
			String resultsRef = null;
			String string = new String(receiver.recv(0)).trim();
			String contents[] = string.split(",");
			if (contents.length == 4) {

				// Filter for referrers
				if (cacheRef.get(contents[1]) != null) {
					resultsRef = cacheRef.get(contents[1]).toString().replaceAll("]", "").replaceAll("\\[", "")
							.replaceAll(" ", "");
				}

				// Writes the response
				String response = contents[2] + "," + contents[0] + "," + resultsRef;

				// Send results to sink
				sender.send(response, 0);
				sender2.send(response, 0);
			}
		}

		sender.close();
		receiver.close();
		context.term();
	}
}
