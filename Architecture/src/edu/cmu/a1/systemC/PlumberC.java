package edu.cmu.a1.systemC;

import edu.cmu.a1.modules.*;
import edu.cmu.a1.systemB.PressureWildPointReplacement;
import edu.cmu.a1.systemB.SinkFilterB;
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
public class PlumberC
{
	public static void main( String argv[])
	{
		/****************************************************************************
		 * Here we instantiate three filters.
		 ****************************************************************************/

		SourceFilter sourceFirstFilter = new SourceFilter(1, 1, Configuration.ReadProperty("SubSetA"));
		SourceFilter sourceSecondFilter = new SourceFilter(1, 1, Configuration.ReadProperty("SubSetB"));
		MergeFilter mergeFilter = new MergeFilter(2,1);
		SplitterFilter splitterFilter1 = new SplitterFilter(1, 2);
		
		AltitudeWildPointLoggingFilter altitudeWildPointLoggingFilter = new AltitudeWildPointLoggingFilter(1, 1);
		SinkFilterC sinkFilterC = new SinkFilterC(1,1, Configuration.ReadProperty("LessThan10K"));
		
		AltitudeWildPointDeletingFilter altitudeWildPointDeletingFilter = new AltitudeWildPointDeletingFilter(1, 1);
		SplitterFilter splitterFilter2 = new SplitterFilter(1, 2);
		
		PressureWildPointFilter pressureWildPointFilter = new PressureWildPointFilter(1, 1);
		SinkFilterB sinkFilterB = new SinkFilterB(1,1,Configuration.ReadProperty("PressureWildPoints"));
		PressureWildPointReplacement pressureWildPointReplacementFilter = new PressureWildPointReplacement(1, 1, Configuration.ReadProperty("OutputC"));

		
	
		/****************************************************************************
		 * Here we connect the filters starting with the sink filter (Filter 1) which
		 * we connect to Filter2 the middle filter. Then we connect Filter2 to the
		 * source filter (Filter3).
		 ****************************************************************************/
		sinkFilterB.Connect(pressureWildPointFilter, 0, 0);
		pressureWildPointFilter.Connect(splitterFilter2, 0, 1);
		pressureWildPointReplacementFilter.Connect(splitterFilter2, 0, 0);
		
		splitterFilter2.Connect(altitudeWildPointDeletingFilter, 0, 0);
		sinkFilterC.Connect(altitudeWildPointLoggingFilter, 0, 0);
		altitudeWildPointDeletingFilter.Connect(splitterFilter1, 0, 0);
		altitudeWildPointLoggingFilter.Connect(splitterFilter1, 0, 1);
		
		splitterFilter1.Connect(mergeFilter, 0, 0);
		mergeFilter.Connect(sourceFirstFilter, 0, 0);
		mergeFilter.Connect(sourceSecondFilter, 1, 0);
		
		/****************************************************************************
		 * Here we start the filters up. All-in-all,... its really kind of boring.
		 ****************************************************************************/
		
		sourceFirstFilter.start();
		sourceSecondFilter.start();
		mergeFilter.start();
		splitterFilter1.start();
		altitudeWildPointDeletingFilter.start();
		altitudeWildPointLoggingFilter.start();
		sinkFilterC.start();
		splitterFilter2.start();
		pressureWildPointFilter.start();
		pressureWildPointReplacementFilter.start();
		sinkFilterB.start();
	} // main

} // Plumber