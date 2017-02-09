package com.uc3m.parallel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.zeromq.ZMQ;

public class Pub {
	public static void main(String[] args) throws Exception {

		int limit = Integer.parseInt(args[0]);
		ZMQ.Context context = ZMQ.context(1);

		// Socket to send messages on
		ZMQ.Socket sender = context.socket(ZMQ.PUSH);
		sender.bind("tcp://*:5557");

		// Socket to send messages on
		ZMQ.Socket sink = context.socket(ZMQ.PUSH);
		sink.connect("tcp://localhost:5558");

		ArrayList<String> packets = readFromFile("files/xaa.csv");

		System.gc();
		long delay = Long.parseLong(args[1]);
		Thread.sleep(2000);
		System.out.println("Sending tasks to workers\n");

		sink.send("0", 0);
		long startTime = System.currentTimeMillis();
		long finTime = startTime + 300000;

		while (!Thread.currentThread().isInterrupted()) {
			for (String message : packets) {
				sender.send(message + "," + System.currentTimeMillis() + "," + limit, 0);
				long start = System.nanoTime();
				while (start + delay >= System.nanoTime());
					
				if (finTime <= System.currentTimeMillis()) {
					break;
				}
			}
			break;

		}

		Thread.sleep(500);
		sink.close();
		sender.close();
		context.term();
	}

	public static ArrayList<String> readFromFile(String path) throws IOException {

		BufferedReader br = null;
		String line = "";
		ArrayList<String> packets = new ArrayList<String>();

		try {

			br = new BufferedReader(new FileReader(path));

			while ((line = br.readLine()) != null) {
				// use comma as separator
				packets.add(line);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();

			}

		}
		System.out.println("Ad Request to send: " + packets.size());
		return packets;
	}

}
