package com.uc3m.parallel;

import org.zeromq.ZMQ;

public class Results {
	public static void main(String[] args) throws Exception {

		// Prepare our context and socket
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket receiver = context.socket(ZMQ.PULL);
		receiver.bind("tcp://*:5558");

		// Wait for start of batch
		String string = new String(receiver.recv(0));
		// int count = 0;
		while (!Thread.currentThread().isInterrupted()) {

			string = new String(receiver.recv(0)).trim();
			@SuppressWarnings("unused")
			String received[] = string.split(",");

		}
		receiver.close();
		context.term();
	}
}
