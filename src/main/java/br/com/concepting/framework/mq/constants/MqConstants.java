package br.com.concepting.framework.mq.constants;

/**
 * Class that defines the constants used in the manipulation of the MQ
 * service.
 *
 * @author fvilarinho
 * @since 3.5.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public final class MqConstants{
    public static final String LISTENER_CLASS_ATTRIBUTE_ID = "listenerClass";
    public static final String QUEUE_ATTRIBUTE_ID = "queue";
    public static final String QUEUES_ATTRIBUTE_ID = "queues";

    public static final String DEFAULT_ID = "mq";
    public static final int DEFAULT_PORT = 61616;
}