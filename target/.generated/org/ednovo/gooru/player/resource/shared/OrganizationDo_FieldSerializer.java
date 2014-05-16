package org.ednovo.gooru.player.resource.shared;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class OrganizationDo_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static java.lang.String getOrganizationCode(org.ednovo.gooru.player.resource.shared.OrganizationDo instance) {
    return (java.lang.String) ReflectionHelper.getField(org.ednovo.gooru.player.resource.shared.OrganizationDo.class, instance, "organizationCode");
  }
  
  private static void setOrganizationCode(org.ednovo.gooru.player.resource.shared.OrganizationDo instance, java.lang.String value) 
  {
    ReflectionHelper.setField(org.ednovo.gooru.player.resource.shared.OrganizationDo.class, instance, "organizationCode", value);
  }
  
  public static void deserialize(SerializationStreamReader streamReader, org.ednovo.gooru.player.resource.shared.OrganizationDo instance) throws SerializationException {
    setOrganizationCode(instance, streamReader.readString());
    
    org.ednovo.gooru.player.resource.shared.PartyDo_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static org.ednovo.gooru.player.resource.shared.OrganizationDo instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new org.ednovo.gooru.player.resource.shared.OrganizationDo();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, org.ednovo.gooru.player.resource.shared.OrganizationDo instance) throws SerializationException {
    streamWriter.writeString(getOrganizationCode(instance));
    
    org.ednovo.gooru.player.resource.shared.PartyDo_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return org.ednovo.gooru.player.resource.shared.OrganizationDo_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    org.ednovo.gooru.player.resource.shared.OrganizationDo_FieldSerializer.deserialize(reader, (org.ednovo.gooru.player.resource.shared.OrganizationDo)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    org.ednovo.gooru.player.resource.shared.OrganizationDo_FieldSerializer.serialize(writer, (org.ednovo.gooru.player.resource.shared.OrganizationDo)object);
  }
  
}
