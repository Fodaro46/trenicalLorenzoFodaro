// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: trenical.proto

// Protobuf Java Version: 3.25.3
package com.trenical.grpc;

public final class TrenicalProto {
  private TrenicalProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_Tratta_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_Tratta_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_CercaTratteRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_CercaTratteRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_CercaTratteResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_CercaTratteResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_UpdateTrattaRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_UpdateTrattaRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_GetOffertaRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_GetOffertaRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_OffertaResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_OffertaResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_TicketRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_TicketRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_TicketResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_TicketResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_LoginRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_LoginRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_LoginResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_LoginResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_BigliettoRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_BigliettoRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_BigliettoResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_BigliettoResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_NotificheRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_NotificheRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_Notifica_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_Notifica_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_UserIdRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_UserIdRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_BigliettoInfo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_BigliettoInfo_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_trenical_StoricoBigliettiResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_trenical_StoricoBigliettiResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016trenical.proto\022\010trenical\032\033google/proto" +
      "buf/empty.proto\"\226\001\n\006Tratta\022\n\n\002id\030\001 \001(\t\022\031" +
      "\n\021stazione_partenza\030\002 \001(\t\022\027\n\017stazione_ar" +
      "rivo\030\003 \001(\t\022\014\n\004data\030\004 \001(\t\022\027\n\017orario_parte" +
      "nza\030\005 \001(\t\022\025\n\rorario_arrivo\030\006 \001(\t\022\016\n\006prez" +
      "zo\030\007 \001(\001\"V\n\022CercaTratteRequest\022\031\n\021stazio" +
      "ne_partenza\030\001 \001(\t\022\027\n\017stazione_arrivo\030\002 \001" +
      "(\t\022\014\n\004data\030\003 \001(\t\"7\n\023CercaTratteResponse\022" +
      " \n\006tratte\030\001 \003(\0132\020.trenical.Tratta\"7\n\023Upd" +
      "ateTrattaRequest\022 \n\006tratta\030\001 \001(\0132\020.treni" +
      "cal.Tratta\"E\n\021GetOffertaRequest\022 \n\006tratt" +
      "a\030\001 \001(\0132\020.trenical.Tratta\022\016\n\006userId\030\002 \001(" +
      "\t\"M\n\017OffertaResponse\022\014\n\004tipo\030\001 \001(\t\022\027\n\017pr" +
      "ezzo_scontato\030\002 \001(\001\022\023\n\013descrizione\030\003 \001(\t" +
      "\"\"\n\rTicketRequest\022\021\n\tticket_id\030\001 \001(\t\"\217\001\n" +
      "\016TicketResponse\022\021\n\tticket_id\030\001 \001(\t\022\026\n\016pa" +
      "ssenger_name\030\002 \001(\t\022\024\n\014train_number\030\003 \001(\t" +
      "\022\026\n\016departure_time\030\004 \001(\t\022\024\n\014arrival_time" +
      "\030\005 \001(\t\022\016\n\006prezzo\030\006 \001(\001\"\035\n\014LoginRequest\022\r" +
      "\n\005email\030\001 \001(\t\"0\n\rLoginResponse\022\016\n\006userId" +
      "\030\001 \001(\t\022\017\n\007message\030\002 \001(\t\"P\n\020BigliettoRequ" +
      "est\022\016\n\006userId\030\001 \001(\t\022\016\n\006tratta\030\002 \001(\t\022\014\n\004d" +
      "ata\030\003 \001(\t\022\016\n\006orario\030\004 \001(\t\"w\n\021BigliettoRe" +
      "sponse\022\023\n\013bigliettoId\030\001 \001(\t\022\r\n\005stato\030\002 \001" +
      "(\t\022\016\n\006prezzo\030\003 \001(\001\022\020\n\010trattaId\030\004 \001(\t\022\014\n\004" +
      "data\030\005 \001(\t\022\016\n\006orario\030\006 \001(\t\"\"\n\020NotificheR" +
      "equest\022\016\n\006userId\030\001 \001(\t\"L\n\010Notifica\022\n\n\002id" +
      "\030\001 \001(\t\022\016\n\006userId\030\002 \001(\t\022\021\n\tmessaggio\030\003 \001(" +
      "\t\022\021\n\ttimestamp\030\004 \001(\t\"\037\n\rUserIdRequest\022\016\n" +
      "\006userId\030\001 \001(\t\"\203\001\n\rBigliettoInfo\022\n\n\002id\030\001 " +
      "\001(\t\022\021\n\ttratta_id\030\002 \001(\t\022\020\n\010partenza\030\003 \001(\t" +
      "\022\016\n\006arrivo\030\004 \001(\t\022\016\n\006prezzo\030\005 \001(\001\022\021\n\ttime" +
      "stamp\030\006 \001(\t\022\016\n\006orario\030\007 \001(\t\"F\n\030StoricoBi" +
      "gliettiResponse\022*\n\tbiglietti\030\001 \003(\0132\027.tre" +
      "nical.BigliettoInfo2\211\004\n\017TrenicalService\022" +
      "B\n\rGetTicketInfo\022\027.trenical.TicketReques" +
      "t\032\030.trenical.TicketResponse\0228\n\005Login\022\026.t" +
      "renical.LoginRequest\032\027.trenical.LoginRes" +
      "ponse\022L\n\021AcquistaBiglietto\022\032.trenical.Bi" +
      "gliettoRequest\032\033.trenical.BigliettoRespo" +
      "nse\022C\n\017StreamNotifiche\022\032.trenical.Notifi" +
      "cheRequest\032\022.trenical.Notifica0\001\022R\n\023GetS" +
      "toricoBiglietti\022\027.trenical.UserIdRequest" +
      "\032\".trenical.StoricoBigliettiResponse\022J\n\013" +
      "CercaTratte\022\034.trenical.CercaTratteReques" +
      "t\032\035.trenical.CercaTratteResponse\022E\n\014Upda" +
      "teTratta\022\035.trenical.UpdateTrattaRequest\032" +
      "\026.google.protobuf.Empty2X\n\020PromotionServ" +
      "ice\022D\n\nGetOfferta\022\033.trenical.GetOffertaR" +
      "equest\032\031.trenical.OffertaResponseB$\n\021com" +
      ".trenical.grpcB\rTrenicalProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.EmptyProto.getDescriptor(),
        });
    internal_static_trenical_Tratta_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_trenical_Tratta_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_Tratta_descriptor,
        new java.lang.String[] { "Id", "StazionePartenza", "StazioneArrivo", "Data", "OrarioPartenza", "OrarioArrivo", "Prezzo", });
    internal_static_trenical_CercaTratteRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_trenical_CercaTratteRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_CercaTratteRequest_descriptor,
        new java.lang.String[] { "StazionePartenza", "StazioneArrivo", "Data", });
    internal_static_trenical_CercaTratteResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_trenical_CercaTratteResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_CercaTratteResponse_descriptor,
        new java.lang.String[] { "Tratte", });
    internal_static_trenical_UpdateTrattaRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_trenical_UpdateTrattaRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_UpdateTrattaRequest_descriptor,
        new java.lang.String[] { "Tratta", });
    internal_static_trenical_GetOffertaRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_trenical_GetOffertaRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_GetOffertaRequest_descriptor,
        new java.lang.String[] { "Tratta", "UserId", });
    internal_static_trenical_OffertaResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_trenical_OffertaResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_OffertaResponse_descriptor,
        new java.lang.String[] { "Tipo", "PrezzoScontato", "Descrizione", });
    internal_static_trenical_TicketRequest_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_trenical_TicketRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_TicketRequest_descriptor,
        new java.lang.String[] { "TicketId", });
    internal_static_trenical_TicketResponse_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_trenical_TicketResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_TicketResponse_descriptor,
        new java.lang.String[] { "TicketId", "PassengerName", "TrainNumber", "DepartureTime", "ArrivalTime", "Prezzo", });
    internal_static_trenical_LoginRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_trenical_LoginRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_LoginRequest_descriptor,
        new java.lang.String[] { "Email", });
    internal_static_trenical_LoginResponse_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_trenical_LoginResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_LoginResponse_descriptor,
        new java.lang.String[] { "UserId", "Message", });
    internal_static_trenical_BigliettoRequest_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_trenical_BigliettoRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_BigliettoRequest_descriptor,
        new java.lang.String[] { "UserId", "Tratta", "Data", "Orario", });
    internal_static_trenical_BigliettoResponse_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_trenical_BigliettoResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_BigliettoResponse_descriptor,
        new java.lang.String[] { "BigliettoId", "Stato", "Prezzo", "TrattaId", "Data", "Orario", });
    internal_static_trenical_NotificheRequest_descriptor =
      getDescriptor().getMessageTypes().get(12);
    internal_static_trenical_NotificheRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_NotificheRequest_descriptor,
        new java.lang.String[] { "UserId", });
    internal_static_trenical_Notifica_descriptor =
      getDescriptor().getMessageTypes().get(13);
    internal_static_trenical_Notifica_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_Notifica_descriptor,
        new java.lang.String[] { "Id", "UserId", "Messaggio", "Timestamp", });
    internal_static_trenical_UserIdRequest_descriptor =
      getDescriptor().getMessageTypes().get(14);
    internal_static_trenical_UserIdRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_UserIdRequest_descriptor,
        new java.lang.String[] { "UserId", });
    internal_static_trenical_BigliettoInfo_descriptor =
      getDescriptor().getMessageTypes().get(15);
    internal_static_trenical_BigliettoInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_BigliettoInfo_descriptor,
        new java.lang.String[] { "Id", "TrattaId", "Partenza", "Arrivo", "Prezzo", "Timestamp", "Orario", });
    internal_static_trenical_StoricoBigliettiResponse_descriptor =
      getDescriptor().getMessageTypes().get(16);
    internal_static_trenical_StoricoBigliettiResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_trenical_StoricoBigliettiResponse_descriptor,
        new java.lang.String[] { "Biglietti", });
    com.google.protobuf.EmptyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
