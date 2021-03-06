package cofh.thermalfoundation.network;

import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import cofh.thermalfoundation.ThermalFoundation;
import cofh.thermalfoundation.gui.container.ContainerLexiconStudy;
import cofh.thermalfoundation.gui.container.ContainerLexiconTransmute;

import net.minecraft.entity.player.EntityPlayer;

public class PacketTFBase extends PacketCoFHBase {

	public static void initialize() {

		PacketHandler.instance.registerPacket(PacketTFBase.class);
	}

	public enum PacketTypes {
		LEXICON_STUDY, LEXICON_TRANSMUTE, CONFIG_SYNC
	}

	@Override
	public void handlePacket(EntityPlayer player, boolean isServer) {

		try {
			int type = getByte();

			switch (PacketTypes.values()[type]) {
			case LEXICON_STUDY:
				if (player.openContainer instanceof ContainerLexiconStudy) {
					// ((ContainerLexiconStudy) player.openContainer).handlePacket(getByte());
				}
				return;
			case LEXICON_TRANSMUTE:
				if (player.openContainer instanceof ContainerLexiconTransmute) {
					((ContainerLexiconTransmute) player.openContainer).handlePacket(getByte());
				}
				return;
			default:
				ThermalFoundation.log.error("Unknown Packet! Internal: TFPH, ID: " + type);
			}
		} catch (Exception e) {
			ThermalFoundation.log.error("Packet payload failure! Please check your config files!");
			e.printStackTrace();
		}
	}

	public static PacketCoFHBase getPacket(PacketTypes theType) {

		return new PacketTFBase().addByte(theType.ordinal());
	}

	public static void sendLexiconTransmutePacketToServer(int command) {

		PacketHandler.sendToServer(getPacket(PacketTypes.LEXICON_TRANSMUTE).addByte(command));
	}

}
