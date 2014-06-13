package co.uk.niadel.mpi.events.client;

import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;

public class EventCrash
{
	/**
	 * The crash report.
	 */
	public CrashReport report;
	
	/**
	 * The thrown exception itself.
	 */
	public ReportedException crash;
	
	public EventCrash(CrashReport report, ReportedException crash)
	{
		this.report = report;
		this.crash = crash;
	}
}
