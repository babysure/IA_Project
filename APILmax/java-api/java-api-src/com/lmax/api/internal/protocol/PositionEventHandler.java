package com.lmax.api.internal.protocol;

import com.lmax.api.FixedPointNumber;

import com.lmax.api.internal.events.PositionEventImpl;
import com.lmax.api.position.PositionEvent;
import com.lmax.api.position.PositionEventListener;

import org.xml.sax.SAXException;

public class PositionEventHandler extends MapBasedHandler
{
    private static final String POSITION = "position";
    private static final String ACCOUNT_ID = "accountId";
    private static final String INSTRUMENT_ID = "instrumentId";
    private static final String VALUATION = "valuation";
    private static final String SHORT_UNFILLED_COST = "shortUnfilledCost";
    private static final String LONG_UNFILLED_COST = "longUnfilledCost";
    private static final String OPEN_QUANTITY = "openQuantity";
    private static final String CUMULATIVE_COST = "cumulativeCost";
    private static final String OPEN_COST = "openCost";

    private PositionEventListener listener;

    public PositionEventHandler()
    {
        super(POSITION);
        addHandler(ACCOUNT_ID);
        addHandler(INSTRUMENT_ID);
        addHandler(VALUATION);
        addHandler(SHORT_UNFILLED_COST);
        addHandler(LONG_UNFILLED_COST);
        addHandler(OPEN_QUANTITY);
        addHandler(CUMULATIVE_COST);
        addHandler(OPEN_COST);
    }

    public void setListener(final PositionEventListener listener)
    {
        this.listener = listener;
    }

    public void endElement(final String tagName) throws SAXException
    {
        if (POSITION.equals(tagName))
        {
            final long accountId = getLongValue(ACCOUNT_ID);
            final long instrumentId = getLongValue(INSTRUMENT_ID);
            final FixedPointNumber valuation = getFixedPointNumberValue(VALUATION);
            final FixedPointNumber shortUnfilledCost = getFixedPointNumberValue(SHORT_UNFILLED_COST);
            final FixedPointNumber longUnfilledCost = getFixedPointNumberValue(LONG_UNFILLED_COST);
            final FixedPointNumber openQuantity = getFixedPointNumberValue(OPEN_QUANTITY);
            final FixedPointNumber cumulativeCost = getFixedPointNumberValue(CUMULATIVE_COST);
            final FixedPointNumber openCost = getFixedPointNumberValue(OPEN_COST);

            PositionEvent positionEvent = new PositionEventImpl(accountId,
                                                                instrumentId,
                                                                valuation,
                                                                shortUnfilledCost,
                                                                longUnfilledCost,
                                                                openQuantity,
                                                                cumulativeCost,
                                                                openCost);

            if (listener != null)
            {
                listener.notify(positionEvent);
            }
        }
    }
}
