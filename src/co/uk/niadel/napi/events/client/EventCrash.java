package co.uk.niadel.napi.events.client;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;

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
}
