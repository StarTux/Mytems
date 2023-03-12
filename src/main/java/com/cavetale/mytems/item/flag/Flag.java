package com.cavetale.mytems.item.flag;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Flag {
    BRITAIN(Mytems.BRITAIN),
    SPAIN(Mytems.SPAIN),
    MEXICO(Mytems.MEXICO),
    USA(Mytems.USA),
    AUSTRIA(Mytems.AUSTRIA),
    BELGIUM(Mytems.BELGIUM),
    DENMARK(Mytems.DENMARK),
    EUROPE(Mytems.EUROPE),
    FRANCE(Mytems.FRANCE),
    GERMANY(Mytems.GERMANY),
    IRELAND(Mytems.IRELAND),
    ITALY(Mytems.ITALY),
    NORWAY(Mytems.NORWAY),
    POLAND(Mytems.POLAND),
    SWEDEN(Mytems.SWEDEN),
    SWITZERLAND(Mytems.SWITZERLAND),
    PRIDE(Mytems.PRIDE_FLAG),
    TRANS_PRIDE(Mytems.TRANS_PRIDE_FLAG),
    ENGLAND(Mytems.ENGLAND),
    CANADA(Mytems.CANADA),
    AUSTRALIA(Mytems.AUSTRALIA),
    NETHERLANDS(Mytems.NETHERLANDS),
    UKRAINE(Mytems.UKRAINE),
    BI_PRIDE(Mytems.BI_FLAG),
    FINLAND(Mytems.FINLAND),
    JAPAN(Mytems.JAPAN),
    ARGENTINA(Mytems.ARGENTINA),
    ;

    public final Mytems mytems;

    public final Flag of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
