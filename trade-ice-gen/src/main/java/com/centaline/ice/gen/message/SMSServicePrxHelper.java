// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.6.2
//
// <auto-generated>
//
// Generated from file `message.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.centaline.ice.gen.message;

/**
 * Provides type-specific helper functions.
 **/
public final class SMSServicePrxHelper extends Ice.ObjectPrxHelperBase implements SMSServicePrx
{
    private static final String __sendSMs_name = "sendSMs";

    public void sendSMs(String msg)
    {
        sendSMs(msg, null, false);
    }

    public void sendSMs(String msg, java.util.Map<String, String> __ctx)
    {
        sendSMs(msg, __ctx, true);
    }

    private void sendSMs(String msg, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        end_sendSMs(begin_sendSMs(msg, __ctx, __explicitCtx, true, null));
    }

    public Ice.AsyncResult begin_sendSMs(String msg)
    {
        return begin_sendSMs(msg, null, false, false, null);
    }

    public Ice.AsyncResult begin_sendSMs(String msg, java.util.Map<String, String> __ctx)
    {
        return begin_sendSMs(msg, __ctx, true, false, null);
    }

    public Ice.AsyncResult begin_sendSMs(String msg, Ice.Callback __cb)
    {
        return begin_sendSMs(msg, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_sendSMs(String msg, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_sendSMs(msg, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_sendSMs(String msg, Callback_SMSService_sendSMs __cb)
    {
        return begin_sendSMs(msg, null, false, false, __cb);
    }

    public Ice.AsyncResult begin_sendSMs(String msg, java.util.Map<String, String> __ctx, Callback_SMSService_sendSMs __cb)
    {
        return begin_sendSMs(msg, __ctx, true, false, __cb);
    }

    public Ice.AsyncResult begin_sendSMs(String msg, 
                                         IceInternal.Functional_VoidCallback __responseCb, 
                                         IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_sendSMs(msg, null, false, false, __responseCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_sendSMs(String msg, 
                                         IceInternal.Functional_VoidCallback __responseCb, 
                                         IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                         IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_sendSMs(msg, null, false, false, __responseCb, __exceptionCb, __sentCb);
    }

    public Ice.AsyncResult begin_sendSMs(String msg, 
                                         java.util.Map<String, String> __ctx, 
                                         IceInternal.Functional_VoidCallback __responseCb, 
                                         IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb)
    {
        return begin_sendSMs(msg, __ctx, true, false, __responseCb, __exceptionCb, null);
    }

    public Ice.AsyncResult begin_sendSMs(String msg, 
                                         java.util.Map<String, String> __ctx, 
                                         IceInternal.Functional_VoidCallback __responseCb, 
                                         IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                         IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_sendSMs(msg, __ctx, true, false, __responseCb, __exceptionCb, __sentCb);
    }

    private Ice.AsyncResult begin_sendSMs(String msg, 
                                          java.util.Map<String, String> __ctx, 
                                          boolean __explicitCtx, 
                                          boolean __synchronous, 
                                          IceInternal.Functional_VoidCallback __responseCb, 
                                          IceInternal.Functional_GenericCallback1<Ice.Exception> __exceptionCb, 
                                          IceInternal.Functional_BoolCallback __sentCb)
    {
        return begin_sendSMs(msg, 
                             __ctx, 
                             __explicitCtx, 
                             __synchronous, 
                             new IceInternal.Functional_OnewayCallback(__responseCb, __exceptionCb, __sentCb));
    }

    private Ice.AsyncResult begin_sendSMs(String msg, 
                                          java.util.Map<String, String> __ctx, 
                                          boolean __explicitCtx, 
                                          boolean __synchronous, 
                                          IceInternal.CallbackBase __cb)
    {
        IceInternal.OutgoingAsync __result = getOutgoingAsync(__sendSMs_name, __cb);
        try
        {
            __result.prepare(__sendSMs_name, Ice.OperationMode.Normal, __ctx, __explicitCtx, __synchronous);
            IceInternal.BasicStream __os = __result.startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(msg);
            __result.endWriteParams();
            __result.invoke();
        }
        catch(Ice.Exception __ex)
        {
            __result.abort(__ex);
        }
        return __result;
    }

    public void end_sendSMs(Ice.AsyncResult __iresult)
    {
        __end(__iresult, __sendSMs_name);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param __obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    public static SMSServicePrx checkedCast(Ice.ObjectPrx __obj)
    {
        return checkedCastImpl(__obj, ice_staticId(), SMSServicePrx.class, SMSServicePrxHelper.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param __obj The untyped proxy.
     * @param __ctx The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    public static SMSServicePrx checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        return checkedCastImpl(__obj, __ctx, ice_staticId(), SMSServicePrx.class, SMSServicePrxHelper.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param __obj The untyped proxy.
     * @param __facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    public static SMSServicePrx checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        return checkedCastImpl(__obj, __facet, ice_staticId(), SMSServicePrx.class, SMSServicePrxHelper.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param __obj The untyped proxy.
     * @param __facet The name of the desired facet.
     * @param __ctx The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    public static SMSServicePrx checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        return checkedCastImpl(__obj, __facet, __ctx, ice_staticId(), SMSServicePrx.class, SMSServicePrxHelper.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param __obj The untyped proxy.
     * @return A proxy for this type.
     **/
    public static SMSServicePrx uncheckedCast(Ice.ObjectPrx __obj)
    {
        return uncheckedCastImpl(__obj, SMSServicePrx.class, SMSServicePrxHelper.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param __obj The untyped proxy.
     * @param __facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    public static SMSServicePrx uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        return uncheckedCastImpl(__obj, __facet, SMSServicePrx.class, SMSServicePrxHelper.class);
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::message::SMSService"
    };

    /**
     * Provides the Slice type ID of this type.
     * @return The Slice type ID.
     **/
    public static String ice_staticId()
    {
        return __ids[1];
    }

    public static void __write(IceInternal.BasicStream __os, SMSServicePrx v)
    {
        __os.writeProxy(v);
    }

    public static SMSServicePrx __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            SMSServicePrxHelper result = new SMSServicePrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }

    public static final long serialVersionUID = 0L;
}
