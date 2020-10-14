package com.test.mavericstest.di.annotations

import javax.inject.Scope

/**
 * Defines Application level scope, any field or method annotated with this will have only a single instance
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope