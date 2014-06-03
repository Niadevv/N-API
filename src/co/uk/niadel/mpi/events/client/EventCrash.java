package co.uk.niadel.mpi.events.client;

import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import co.uk.niadel.mpi.events.IEvent;

public class EventCrash implements IEvent
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

	@Override
	public String getName()
	{
		return "EventCrash";
	}
}
