package org.shaza4061.bigpay;

import org.shaza4061.bigpay.manager.NetworkManager;
import org.shaza4061.bigpay.manager.impl.NetworkManagerImpl;
import org.shaza4061.bigpay.manager.impl.TrainConductorImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CommandProcessor commandProcessor = new DeliveryNetworkCommandProcessor();
        List<String> commands = new ArrayList<>();
        if (args.length < 1) {
            System.out.println("Usage: java AutonomousMailTrain <filename>");
            return;
        }

        String filename = args[0];

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                int commentIndex = line.indexOf("//");
                if (commentIndex != -1) {
                    line = line.substring(0, commentIndex).trim();
                }

                // Print the line if it's not empty
                if (!line.isEmpty()) {
                    commands.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        NetworkManager networkManager = commandProcessor.process(commands);
        networkManager.getTrains().forEach(train -> {
            train.getTrainConductor().start();
        });
    }

}