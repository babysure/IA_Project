package com.lmax.api.internal.protocol;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lmax.api.FixedPointNumber;
import com.lmax.api.internal.DayOfWeekHandler;
import com.lmax.api.internal.ListHandler;
import com.lmax.api.internal.orderbook.CalendarInfoImpl;
import com.lmax.api.internal.orderbook.CommercialInfoImpl;
import com.lmax.api.internal.orderbook.ContractInfoImpl;
import com.lmax.api.internal.orderbook.InstrumentImpl;
import com.lmax.api.internal.orderbook.OrderBookInfoImpl;
import com.lmax.api.internal.orderbook.RiskInfoImpl;
import com.lmax.api.internal.orderbook.UnderlyingInfoImpl;
import com.lmax.api.orderbook.CalendarInfo;
import com.lmax.api.orderbook.CommercialInfo;
import com.lmax.api.orderbook.ContractInfo;
import com.lmax.api.orderbook.DayOfWeek;
import com.lmax.api.orderbook.Instrument;
import com.lmax.api.orderbook.OrderBookInfo;
import com.lmax.api.orderbook.RiskInfo;
import com.lmax.api.orderbook.UnderlyingInfo;

import org.xml.sax.SAXException;

public class SearchInstrumentResponseHandler extends MapBasedHandler
{
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String OPENING_OFFSET = "openingOffset";
    private static final String CLOSING_OFFSET = "closingOffset";
    private static final String TRADING_DAY = "tradingDay";
    private static final String TIMEZONE = "timezone";
    private static final String MARGIN = "margin";
    private static final String CURRENCY = "currency";
    private static final String UNIT_PRICE = "unitPrice";
    private static final String ORDER_QUANTITY_INCREMENT = "orderQuantityIncrement";
    private static final String PRICE_INCREMENT = "priceIncrement";
    private static final String ASSET_CLASS = "assetClass";
    private static final String UNDERLYING_ISIN = "underlyingIsin";
    private static final String SYMBOL = "symbol";
    private static final String MAXIMUM_POSITION_THRESHOLD = "maximumPositionThreshold";
    private static final String AGGRESSIVE_COMMISSION_RATE = "aggressiveCommissionRate";
    private static final String PASSIVE_COMMISSION_RATE = "passiveCommissionRate";
    private static final String MINIMUM_COMMISSION = "minimumCommission";
    private static final String AGGRESSIVE_COMMISSION_PER_CONTRACT = "aggressiveCommissionPerContract";
    private static final String PASSIVE_COMMISSION_PER_CONTRACT = "passiveCommissionPerContract";
    private static final String FUNDING_PREMIUM_PERCENTAGE = "fundingPremiumPercentage";
    private static final String FUNDING_REDUCTION_PERCENTAGE = "fundingReductionPercentage";
    private static final String FUNDING_BASE_RATE = "fundingBaseRate";
    private static final String DAILY_INTEREST_RATE_BASIS = "dailyInterestRateBasis";
    private static final String CONTRACT_UNIT_MEASURE = "contractUnitOfMeasure";
    private static final String CONTRACT_SIZE = "contractSize";
    private static final String RETAIL_VOLATILITY_BAND_PERCENTAGE = "retailVolatilityBandPercentage";
    private static final String HAS_MORE_RESULTS_TAG = "hasMoreResults";
    private static final String LONG_SWAP_POINTS = "longSwapPoints";
    private static final String SHORT_SWAP_POINTS = "shortSwapPoints";


    private final List<Instrument> instruments = new ArrayList<>();
    private final ListHandler<DayOfWeek> tradingDaysHandler = new DayOfWeekHandler(TRADING_DAY);
    private String status;
    private String failureMessage;
    private boolean hasMoreResults;

    public SearchInstrumentResponseHandler()
    {
        super(BODY);
        addHandler(STATUS);
        addHandler(MESSAGE);

        addHandler(ID);
        addHandler(NAME);
        addHandler(START_TIME);
        addHandler(END_TIME);
        addHandler(OPENING_OFFSET);
        addHandler(CLOSING_OFFSET);
        addHandler(TIMEZONE);
        addHandler(MARGIN);
        addHandler(CURRENCY);
        addHandler(UNIT_PRICE);
        addHandler(ORDER_QUANTITY_INCREMENT);
        addHandler(PRICE_INCREMENT);
        addHandler(ASSET_CLASS);
        addHandler(UNDERLYING_ISIN);
        addHandler(SYMBOL);
        addHandler(MAXIMUM_POSITION_THRESHOLD);
        addHandler(AGGRESSIVE_COMMISSION_RATE);
        addHandler(PASSIVE_COMMISSION_RATE);
        addHandler(MINIMUM_COMMISSION);
        addHandler(AGGRESSIVE_COMMISSION_PER_CONTRACT);
        addHandler(PASSIVE_COMMISSION_PER_CONTRACT);
        addHandler(FUNDING_PREMIUM_PERCENTAGE);
        addHandler(FUNDING_REDUCTION_PERCENTAGE);
        addHandler(LONG_SWAP_POINTS);
        addHandler(SHORT_SWAP_POINTS);
        addHandler(FUNDING_BASE_RATE);
        addHandler(DAILY_INTEREST_RATE_BASIS);
        addHandler(CONTRACT_UNIT_MEASURE);
        addHandler(CONTRACT_SIZE);
        addHandler(RETAIL_VOLATILITY_BAND_PERCENTAGE);
        addHandler(tradingDaysHandler);
        addHandler(HAS_MORE_RESULTS_TAG);
    }

    public List<Instrument> getInstruments()
    {
        return instruments;
    }

    @Override
    public void endElement(final String endElement) throws SAXException
    {
        if ("instrument".equals(endElement))
        {
            instruments.add(parseInstrument());
        }
        else if (BODY.equals(endElement))
        {
            status = getStringValue(STATUS);
            failureMessage = getStringValue(MESSAGE);
            if (OK.equals(status))
            {
                hasMoreResults = getBooleanValue(HAS_MORE_RESULTS_TAG);
            }
        }
    }

    private InstrumentImpl parseInstrument() throws SAXException
    {
        final long id = getLongValue(ID);
        final String name = getStringValue(NAME);

        final String symbol = getStringValue(SYMBOL);
        final String isin = getStringValue(UNDERLYING_ISIN);
        final String assetClass = getStringValue(ASSET_CLASS);

        final UnderlyingInfo underlying = new UnderlyingInfoImpl(symbol, isin, assetClass);

        final Date startTime;
        try
        {
            startTime = getDate(START_TIME);
        }
        catch (final ParseException e)
        {
            throw new SAXException(e);
        }

        final Date expiryTime;
        try
        {
            expiryTime = getDate(END_TIME);
        }
        catch (final ParseException e)
        {
            throw new SAXException(e);
        }
        final int openOffset = getIntValue(OPENING_OFFSET);
        final int closeOffset = getIntValue(CLOSING_OFFSET);
        final String timeZone = getStringValue(TIMEZONE);
        final List<DayOfWeek> daysOfWeek = tradingDaysHandler.getContentList();
        tradingDaysHandler.reset();

        final CalendarInfo calendarInfo = new CalendarInfoImpl(startTime, expiryTime, openOffset, closeOffset, timeZone, daysOfWeek);

        final FixedPointNumber marginRate = getFixedPointNumberValue(MARGIN);
        final FixedPointNumber maximumPosition = getFixedPointNumberValue(MAXIMUM_POSITION_THRESHOLD);

        final RiskInfo riskInfo = new RiskInfoImpl(marginRate, maximumPosition);

        final FixedPointNumber priceIncrement = getFixedPointNumberValue(PRICE_INCREMENT);
        final FixedPointNumber quantityIncrement = getFixedPointNumberValue(ORDER_QUANTITY_INCREMENT);
        final FixedPointNumber volatilityBandPercentage = getFixedPointNumberValue(RETAIL_VOLATILITY_BAND_PERCENTAGE);

        final OrderBookInfo orderBookInfo = new OrderBookInfoImpl(priceIncrement, quantityIncrement, volatilityBandPercentage);

        final String currency = getStringValue(CURRENCY);
        final FixedPointNumber unitPrice = getFixedPointNumberValue(UNIT_PRICE);
        final String unitOfMeasure = getStringValue(CONTRACT_UNIT_MEASURE);
        final FixedPointNumber contractSize = getFixedPointNumberValue(CONTRACT_SIZE);

        final ContractInfo contractInfo = new ContractInfoImpl(currency, unitPrice, unitOfMeasure, contractSize);

        final FixedPointNumber minimumCommission = getFixedPointNumberValue(MINIMUM_COMMISSION);
        final FixedPointNumber aggressiveCommissionRate = getFixedPointNumberValue(AGGRESSIVE_COMMISSION_RATE);
        final FixedPointNumber passiveCommissionRate = getFixedPointNumberValue(PASSIVE_COMMISSION_RATE);
        final FixedPointNumber aggressiveCommissionPerContract = getFixedPointNumberValue(AGGRESSIVE_COMMISSION_PER_CONTRACT);
        final FixedPointNumber passiveCommissionPerContract = getFixedPointNumberValue(PASSIVE_COMMISSION_PER_CONTRACT);
        final String fundingBaseRate = getOptionalStringValue(FUNDING_BASE_RATE);
        final int dailyInterestRateBasis = getIntValue(DAILY_INTEREST_RATE_BASIS);
        final FixedPointNumber fundingPremiumPercentage = getFixedPointNumberValue(FUNDING_PREMIUM_PERCENTAGE);
        final FixedPointNumber fundingReductionPercentage = getFixedPointNumberValue(FUNDING_REDUCTION_PERCENTAGE);
        final FixedPointNumber longSwapPoints = getFixedPointNumberValue(LONG_SWAP_POINTS);
        final FixedPointNumber shortSwapPoints = getFixedPointNumberValue(SHORT_SWAP_POINTS);

        final CommercialInfo commercialInfo = new CommercialInfoImpl(minimumCommission, aggressiveCommissionRate, passiveCommissionRate,
                                                                     aggressiveCommissionPerContract, passiveCommissionPerContract,
                                                                     fundingPremiumPercentage, fundingReductionPercentage, fundingBaseRate, dailyInterestRateBasis,
                                                                     longSwapPoints, shortSwapPoints);

        return new InstrumentImpl(id, name, underlying, calendarInfo, riskInfo, orderBookInfo, contractInfo, commercialInfo);
    }

    public boolean isOk()
    {
        return OK.equals(status);
    }

    public String getMessage()
    {
        return failureMessage;
    }

    public boolean getHasMoreResults()
    {
        return hasMoreResults;
    }
}
