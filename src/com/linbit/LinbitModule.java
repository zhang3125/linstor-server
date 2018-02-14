package com.linbit;

import com.google.inject.AbstractModule;

public class LinbitModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(WorkQueue.class).to(WorkerPool.class);
    }
}
