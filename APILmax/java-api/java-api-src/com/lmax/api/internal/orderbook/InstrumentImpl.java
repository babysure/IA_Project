package com.lmax.api.internal.orderbook;

import com.lmax.api.orderbook.CalendarInfo;
import com.lmax.api.orderbook.CommercialInfo;
import com.lmax.api.orderbook.ContractInfo;
import com.lmax.api.orderbook.Instrument;
import com.lmax.api.orderbook.OrderBookInfo;
import com.lmax.api.orderbook.RiskInfo;
import com.lmax.api.orderbook.UnderlyingInfo;

public class InstrumentImpl implements Instrument
{

    private final long id;
    private final String name;
    private final UnderlyingInfo underlying;
    private final CalendarInfo calendar;
    private final RiskInfo risk;
    private final OrderBookInfo orderBook;
    private final ContractInfo contract;
    private final CommercialInfo commercial;

    public InstrumentImpl(final long id, final String name, final UnderlyingInfo underlying, final CalendarInfo calendar,
                          final RiskInfo risk, final OrderBookInfo orderBook, final ContractInfo contract,
                          final CommercialInfo commercial)
    {
        this.id = id;
        this.name = name;
        this.underlying = underlying;
        this.calendar = calendar;
        this.risk = risk;
        this.orderBook = orderBook;
        this.contract = contract;
        this.commercial = commercial;
    }

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public UnderlyingInfo getUnderlying()
    {
        return underlying;
    }

    @Override
    public CalendarInfo getCalendar()
    {
        return calendar;
    }

    @Override
    public RiskInfo getRisk()
    {
        return risk;
    }

    @Override
    public OrderBookInfo getOrderBook()
    {
        return orderBook;
    }

    @Override
    public ContractInfo getContract()
    {
        return contract;
    }

    @Override
    public CommercialInfo getCommercial()
    {
        return commercial;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        { return true; }
        if (o == null || getClass() != o.getClass())
        { return false; }

        final InstrumentImpl that = (InstrumentImpl)o;

        if (id != that.id)
        { return false; }
        if (calendar != null ? !calendar.equals(that.calendar) : that.calendar != null)
        { return false; }
        if (commercial != null ? !commercial.equals(that.commercial) : that.commercial != null)
        { return false; }
        if (contract != null ? !contract.equals(that.contract) : that.contract != null)
        { return false; }
        if (name != null ? !name.equals(that.name) : that.name != null)
        { return false; }
        if (orderBook != null ? !orderBook.equals(that.orderBook) : that.orderBook != null)
        { return false; }
        if (risk != null ? !risk.equals(that.risk) : that.risk != null)
        { return false; }
        if (underlying != null ? !underlying.equals(that.underlying) : that.underlying != null)
        { return false; }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int)(id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (underlying != null ? underlying.hashCode() : 0);
        result = 31 * result + (calendar != null ? calendar.hashCode() : 0);
        result = 31 * result + (risk != null ? risk.hashCode() : 0);
        result = 31 * result + (orderBook != null ? orderBook.hashCode() : 0);
        result = 31 * result + (contract != null ? contract.hashCode() : 0);
        result = 31 * result + (commercial != null ? commercial.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "InstrumentImpl{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", underlying=" + underlying +
               ", calendar=" + calendar +
               ", risk=" + risk +
               ", orderBook=" + orderBook +
               ", contract=" + contract +
               ", commercial=" + commercial +
               '}';
    }
}
