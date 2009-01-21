package com.atech.utils.logs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 *  This file is part of ATech Tools library.
 *  
 *  RedirectScreeen
 *  
 *  <P>
 *  Redirects all System.out.println() calls to the Log4J system. If the Log4J system is
 *  setup to use a ConsoleAppender, all System.out.println() calls will go to the <b>out</b>
 *  stream as well as be redirected to the Log4J appenders. We ensure this by making sure that
 *  any ConsoleAppender's <b>Follow</b> flag is set to <b>false</b>.
 *  </P>
 *
 *  <P>
 *  All System.out.println() calls are assumed to by intended for output at the DEBUG
 *  level.
 * </P>
 *
 *  <P>
 *  <b>Usage:</b>
 *  </P>
 *
 *  <blockquote>
 *      new SystemOutRedirectToLog4J() ;    //  That's all!
 *  <blockquote>
 *  
 *  Copyright (C) 2008  Gautam Satpathy 
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *
 *  
 *  @author : Gautam Satpathy (gautam@satpathy.org)
 *  @version : 0.1b
 *  @date : Jan 25, 2008
 *
*/



public class RedirectScreen 
{

	private static final String LINE_SEP = System.getProperty( "line.separator" );

	//	Our logger.
	private Logger logger ;

	/**
	 * Constructor
	 */
	public RedirectScreen() {
		super() ;

		//	Initialize
		initialize() ;
	}

	/**
	 *	Initialize the redirection.
	 */
	private void initialize() 
	{
		PrintStream			sysOut ;
		LogOutputStream 	outStream ;

		//	Get a Logger reference for us to use.
		logger 		= Logger.getLogger( RedirectScreen.class ) ;

		//	In case the Log4J system is setup to use a ConsoleAppender, we must make sure
		//	that the Follow option is set to False. If it is left as true, ConsoleAppender
		// 	will also send output to the System.out stream which we are redirecting back to
		//	Log4J! In effect we will allow an infinite loop, resulting in a StackOverflow.
		//	To avoid this we look for a ConsoleAppender in the list of appenders and then
		// 	check if the Follow option has been set to true. If so we reset it to false and
		//	reinitialize the ConsoleAppender.
		Enumeration<?> e = logger.getLoggerRepository().getRootLogger().getAllAppenders() ;
		while ( e.hasMoreElements() ) {
			Appender a = ( Appender) e.nextElement() ;
			if ( a instanceof ConsoleAppender )	
			{
			    
				if ( ((ConsoleAppender) a).getFollow() ) {
					((ConsoleAppender) a).setFollow( false ) ;
					((ConsoleAppender) a).activateOptions() ;
				}
				logger.removeAppender( a ) ;
			}
		}

		//	Do the redirection.
		outStream 	= new LogOutputStream() ;
		sysOut		= new PrintStream( outStream, true ) ;
		System.setOut(sysOut) ;
		
		// Redirection to Error 
        LogOutputStreamError errOutStream = new LogOutputStreamError();
        PrintStream sysErr = new PrintStream(errOutStream, true);
		System.setErr(sysErr);
	}

	/**
	 * 	OutputStream implementation for our redirect to Log4j.
	 */
	class LogOutputStream extends ByteArrayOutputStream 
	{

		/**
		 * 	Default constructor.
		 */
		LogOutputStream() {
			super() ;
		}

		/**
		 *
		 * @throws IOException
		 */
		public void flush() throws IOException {
			//
			//	We now write to the Log4J system.
			//

			//	Get a String representation of the buffer
			String message = toString() ;

			//	Check if the String only contains a New Line or is empty. It not, send it to
			// 	Log4J as a DEBUG level message
			if ( !LINE_SEP.equals(message) && !"".equals(message) )	{
				logger.debug( message ) ;
			}

			//	Lastly we reset the underlying buffer so that it can be used again.
			reset() ;
		}

	}	/*	End of the LogOutputStream class. */

	
    /**
     *  OutputStream implementation for our redirect to Log4j.
     */
    class LogOutputStreamError extends ByteArrayOutputStream 
    {

        /**
         *  Default constructor.
         */
        LogOutputStreamError() {
            super() ;
        }

        /**
         *
         * @throws IOException
         */
        public void flush() throws IOException 
        {
            //
            //  We now write to the Log4J system.
            //

            //  Get a String representation of the buffer
            String message = toString() ;

            //  Check if the String only contains a New Line or is empty. It not, send it to
            //  Log4J as a DEBUG level message
            if ( !LINE_SEP.equals(message) && !"".equals(message) ) 
            {
                logger.error( message ) ;
            }

            //  Lastly we reset the underlying buffer so that it can be used again.
            reset() ;
        }

    }   /*  End of the LogOutputStream class. */
	
	
	/**
	 *  Testing.
	 *
	 * 	@param args
	 */
	public static void main( String[] args ) {
		//	Configure Log4J
		java.util.Properties props = new java.util.Properties();
		props.setProperty("log4j.rootLogger", "DEBUG, R, C");
		props.setProperty("log4j.appender.C", "org.apache.log4j.ConsoleAppender" ) ;
		props.setProperty("log4j.appender.C.layout", "org.apache.log4j.PatternLayout");
		props.setProperty("log4j.appender.C.Follow", "true" ) ;	//	Test our safety code!
		props.setProperty("log4j.appender.C.layout.ConversionPattern", "[%F - %L] [%d{dd MMM yyyy HH:mm:ss,SSS}] - %m%n");
		props.setProperty("log4j.appender.R", "org.apache.log4j.FileAppender");
		props.setProperty("log4j.appender.R.File", "execution.log");
		props.setProperty("log4j.appender.R.Append", "false");
		props.setProperty("log4j.appender.R.layout", "org.apache.log4j.PatternLayout");
		props.setProperty("log4j.appender.R.layout.ConversionPattern", "[%F - %L] [%d{dd MMM yyyy HH:mm:ss,SSS}] - %m%n");
		PropertyConfigurator.configure(props);

		//	Run the Test.
		System.out.println( "Writing to System.out BEFORE initializing the redirector." ) ;
		new RedirectScreen() ;
		System.out.println( "Writing to System.out AFTER initializing the redirector." ) ;
		System.out.println( "Another log message." ) ;
		System.out.println( "And Another log message." ) ;
		System.out.println( "Yet Another log message." ) ;

		System.out.println( "Okay. All done. I am out of here!" ) ;
		System.exit( 0 ) ;
	}

}	/*	End of the SystemOutRedirectToLog4j class. */