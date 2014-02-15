package edu.cmu.a1.systemB;

import edu.cmu.a1.modules.*;
import edu.cmu.a1.util.Configuration;


/******************************************************************************************************************
 * File:Plumber.java
 * Course: 17655
 * Project: Assignment 1
 * Copyright: Copyright (c) 2003 Carnegie Mellon University
 * Versions:
 *	1.0 November 2008 - Sample Pipe and Filter code (ajl).
 *
 * Description:
 *
 * This class serves as an example to illstrate how to use the PlumberTemplate to create a main thread that
 * instantiates and connects a set of filters. This example consists of three filters: a source, a middle filter
 * that acts as a pass-through filter (it does nothing to the data), and a sink filter which illustrates all kinds
 * of useful things that you can do with the input stream of data.
 *
 * Parameters: 		None
 *
 * Internal Methods:	None
 *
 ******************************************************************************************************************/
public class PlumberB
{
	public static void main( String args[])
	{
		/****************************************************************************
		 * Here we instantiate three filters.
		 ****************************************************************************/
		String inputFileName = Configuration.ReadProperty("FlightData");
		String wildPointFileName = Configuration.ReadProperty("WildPointData");
		//String outputBFileName = "D:/MSIT-SE/Architecture/Group Assignment/DataSets/outputB.dat";
		SourceFilter sourceFilter = new SourceFilter(1,1,inputFileName);
		AltitudeConvertFilter altitudeConvertFilter = new AltitudeConvertFilter(1,1);
		TemperatureFilter temperatureFilter = new TemperatureFilter(1,1);
		
		
		SplitterFilter splitterFilter = new SplitterFilter(1, 1);
		PressureWildPointFilter pressureWildPointFilter = new PressureWildPointFilter(1, 1);
		SinkFilterB2 sinkFilterB2 = new SinkFilterB2(1,1,wildPointFileName);
		/****************************************************************************
		 * Here we connect the filters starting with the sink filter (Filter 1) which
		 * we connect to Filter2 the middle filter. Then we connect Filter2 to the
		 * source filter (Filter3).
		 ****************************************************************************/
		sinkFilterB2.Connect(pressureWildPointFilter, 0, 0);
		pressureWildPointFilter.Connect(splitterFilter, 0,0);
		splitterFilter.Connect(altitudeConvertFilter,0,0); // This esstially says, "connect sinkFilter input port to altitudeConvertFilter output port
		
		
		altitudeConvertFilter.Connect(temperatureFilter, 0, 0); // This esstially says, "connect altitudeConvertFilter intput port to temperatureFilter output port
		temperatureFilter.Connect(sourceFilter, 0, 0);// This esstially says, "connect temperatureFilter input port to sourceFilter output port
		
		
		/****************************************************************************
		 * Here we start the filters up. All-in-all,... its really kind of boring.
		 ****************************************************************************/
		
		sourceFilter.start();
		altitudeConvertFilter.start();
		temperatureFilter.start();
		sinkFilterB2.start();
		splitterFilter.start();
		pressureWildPointFilter.start();

	} // main

} // Plumber