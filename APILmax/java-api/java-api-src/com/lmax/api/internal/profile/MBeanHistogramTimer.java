package com.lmax.api.internal.profile;

import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.DynamicMBean;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.ObjectName;

import com.lmax.api.internal.collections.Histogram;
import com.lmax.api.profile.TimingListener;

public class MBeanHistogramTimer implements TimingListener, DynamicMBean
{
    private final long[] bounds = createBounds();
    private final Histogram histogram = new Histogram(bounds);
    private final Map<String, Integer> nameToValue;
    private final String shortName;

    public MBeanHistogramTimer(String shortName)
    {
        this.shortName = shortName;
        Map<String, Integer> tmp = new HashMap<>();
        for (int i = 0; i < bounds.length; i++)
        {
            tmp.put(pad(bounds[i]), i);
        }
        nameToValue = Collections.unmodifiableMap(tmp);
    }

    @Override
    public void notify(String name, long elapsedTimeNanos)
    {
        histogram.addObservation(elapsedTimeNanos / 1000);
    }

    @Override
    public Object getAttribute(String attribute)
    {
        Integer index = nameToValue.get(attribute);

        if (null == index)
        {
            return null;
        }

        return new Attribute(attribute, histogram.getCountAt(index));
    }

    @Override
    public void setAttribute(Attribute attribute)
    {
    }

    @Override
    public AttributeList getAttributes(String[] attributes)
    {
        AttributeList attributeList = new AttributeList();

        for (String attribute : attributes)
        {
            attributeList.add(getAttribute(attribute));
        }

        return attributeList;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes)
    {
        return null;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature)
    {
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo()
    {
        MBeanAttributeInfo[] attributeInfos = new MBeanAttributeInfo[bounds.length];
        for (int i = 0, n = bounds.length; i < n; i++)
        {
            attributeInfos[i] = new MBeanAttributeInfo(pad(bounds[i]), "long", "Bound-" + String.valueOf(bounds[i]), true, false, false);
        }
        return new MBeanInfo(this.getClass().getName(), "Java API Callback Timing", attributeInfos, null, null, null);
    }

    public static String pad(long l)
    {
        return String.format("%1$10s", String.valueOf(l));
    }

    @SuppressWarnings("checkstyle:regexpsinglelinejava")
    public String register()
    {
        String jmxName = "com.lmax:type=timer,name=" + shortName + ",id=" + System.identityHashCode(this);
        try
        {
            ObjectName name = new ObjectName(jmxName);
            ManagementFactory.getPlatformMBeanServer().registerMBean(this, name);
        }
        catch (Exception e)
        {
            System.err.println("Failed to register mbean: " + jmxName);
            e.printStackTrace();
        }

        return shortName;
    }

    private static long[] createBounds()
    {
        long[] bounds = new long[32];
        long bound = 1;
        for (int i = 0; i < bounds.length; i++)
        {
            bounds[i] = bound;
            bound *= 2;
        }

        return bounds;
    }
}
