// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: trenical.proto

// Protobuf Java Version: 3.25.3
package com.trenical.grpc;

public interface TicketResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:trenical.TicketResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string ticket_id = 1;</code>
   * @return The ticketId.
   */
  java.lang.String getTicketId();
  /**
   * <code>string ticket_id = 1;</code>
   * @return The bytes for ticketId.
   */
  com.google.protobuf.ByteString
      getTicketIdBytes();

  /**
   * <code>string passenger_name = 2;</code>
   * @return The passengerName.
   */
  java.lang.String getPassengerName();
  /**
   * <code>string passenger_name = 2;</code>
   * @return The bytes for passengerName.
   */
  com.google.protobuf.ByteString
      getPassengerNameBytes();

  /**
   * <code>string train_number = 3;</code>
   * @return The trainNumber.
   */
  java.lang.String getTrainNumber();
  /**
   * <code>string train_number = 3;</code>
   * @return The bytes for trainNumber.
   */
  com.google.protobuf.ByteString
      getTrainNumberBytes();

  /**
   * <code>string departure_time = 4;</code>
   * @return The departureTime.
   */
  java.lang.String getDepartureTime();
  /**
   * <code>string departure_time = 4;</code>
   * @return The bytes for departureTime.
   */
  com.google.protobuf.ByteString
      getDepartureTimeBytes();

  /**
   * <code>string arrival_time = 5;</code>
   * @return The arrivalTime.
   */
  java.lang.String getArrivalTime();
  /**
   * <code>string arrival_time = 5;</code>
   * @return The bytes for arrivalTime.
   */
  com.google.protobuf.ByteString
      getArrivalTimeBytes();

  /**
   * <code>double prezzo = 6;</code>
   * @return The prezzo.
   */
  double getPrezzo();
}
