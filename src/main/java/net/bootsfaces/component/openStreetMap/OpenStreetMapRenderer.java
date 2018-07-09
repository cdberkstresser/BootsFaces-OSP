/**
 *  Copyright 2014-2017 Riccardo Massera (TheCoder4.Eu) and Stephan Rauh (http://www.beyondjava.net).
 *
 *  This file is part of BootsFaces.
 *
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
 */

package net.bootsfaces.component.openStreetMap;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import net.bootsfaces.component.ajax.AJAXRenderer;
import net.bootsfaces.render.CoreRenderer;

/** This class generates the HTML code of &lt;b:openStreetMap /&gt;. */
@FacesRenderer(componentFamily = "net.bootsfaces.component", rendererType = "net.bootsfaces.component.openStreetMap.OpenStreetMap")
public class OpenStreetMapRenderer extends CoreRenderer {

	@Override
	public void decode(FacesContext context, UIComponent component) {
		OpenStreetMap openStreetMap = (OpenStreetMap) component;

		decodeBehaviors(context, openStreetMap); // f:ajax

		String clientId = openStreetMap.getClientId(context);
		new AJAXRenderer().decode(context, component, clientId);
	}

	/**
	 * This methods generates the HTML code of the current b:openStreetMap.
	 * <code>encodeBegin</code> generates the start of the component. After the, the
	 * JSF framework calls <code>encodeChildren()</code> to generate the HTML code
	 * between the beginning and the end of the component. For instance, in the case
	 * of a panel component the content of the panel is generated by
	 * <code>encodeChildren()</code>. After that, <code>encodeEnd()</code> is called
	 * to generate the rest of the HTML code.
	 *
	 * @param context   the FacesContext.
	 * @param component the current b:openStreetMap.
	 * @throws IOException thrown if something goes wrong when writing the HTML
	 *                     code.
	 */
	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		
		if (!component.isRendered()) {
			return;
		}
		OpenStreetMap openStreetMap = (OpenStreetMap) component;

		ResponseWriter rw = context.getResponseWriter();
		String clientId = openStreetMap.getClientId();

		rw.startElement("div", openStreetMap);
		rw.writeAttribute("id", clientId, "id");
		rw.writeAttribute("style", "width:" + openStreetMap.getWidth() + "; height:" + openStreetMap.getHeight(), null);

	}

	/**
	 * This methods generates the HTML code of the current b:openStreetMap.
	 * <code>encodeBegin</code> generates the start of the component. After the, the
	 * JSF framework calls <code>encodeChildren()</code> to generate the HTML code
	 * between the beginning and the end of the component. For instance, in the case
	 * of a panel component the content of the panel is generated by
	 * <code>encodeChildren()</code>. After that, <code>encodeEnd()</code> is called
	 * to generate the rest of the HTML code.
	 *
	 * @param context   the FacesContext.
	 * @param component the current b:openStreetMap.
	 * @throws IOException thrown if something goes wrong when writing the HTML
	 *                     code.
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered()) {
			return;
		}
		OpenStreetMap openStreetMap = (OpenStreetMap) component;
		ResponseWriter rw = context.getResponseWriter();
		String clientIdRaw = openStreetMap.getClientId();
		String clientId = clientIdRaw.replace(":", "");

		rw.endElement("div");

		rw.startElement("script", component);
		rw.writeText("var " + clientId + "_map = L.map('" + clientId + "', {center: [" + openStreetMap.getCenter()
				+ "], zoom: " + openStreetMap.getZoom() + ", layers: L.tileLayer('" + openStreetMap.getUrlTemplate()
				+ "', {id: 'osm', attribution: '" + openStreetMap.getAttribution() + "', maxZoom: "
				+ openStreetMap.getMaxZoom() + ", minZoom: " + openStreetMap.getMinZoom() + "}), dragging:"
				+ openStreetMap.getDragging() + ", zoomControl:" + openStreetMap.getZoomControl() + " });", null);
		rw.writeText("if('" + openStreetMap.getMarker() + "')", null);
		rw.writeText("{", null);
		rw.writeText("var " + clientId + "_marker = L.marker([" + openStreetMap.getMarker()
				+ "],{icon: new L.Icon({iconSize: [25, 41], iconAnchor: [25, 41], popupAnchor: [-12, -45], iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-icon.png', shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.3.1/images/marker-shadow.png'})}).addTo("
				+ clientId + "_map);", null);
		rw.writeText("if('" + openStreetMap.getPopupMsg() + "')", null);
		rw.writeText(clientId + "_marker.bindPopup('" + openStreetMap.getPopupMsg() + "');", null);
		rw.writeText("}", null);
		rw.writeText("if(!" + openStreetMap.getZoomGlobal() + ")", null);
		rw.writeText("{", null);
		rw.writeText(clientId + "_map.touchZoom.disable();", null);
		rw.writeText(clientId + "_map.doubleClickZoom.disable();", null);
		rw.writeText(clientId + "_map.scrollWheelZoom.disable();", null);
		rw.writeText(clientId + "_map.boxZoom.disable();", null);
		rw.writeText(clientId + "_map.keyboard.disable();", null);
		rw.writeText("}", null);
		rw.writeText("if(" + openStreetMap.getMiniMap() + ")", null);
		rw.writeText("{", null);
		rw.writeText("new L.Control.MiniMap(L.tileLayer('" + openStreetMap.getUrlTemplate() + "', {}), {", null);
		rw.writeText("toggleDisplay: true,", null);
		rw.writeText("zoomAnimation: true,", null);
		rw.writeText("position: '" + openStreetMap.getMiniMapPosition() + "',", null);
		rw.writeText("width: " + openStreetMap.getMiniMapWidth() + ",", null);
		rw.writeText("height: " + openStreetMap.getMiniMapWidth(), null);
		rw.writeText("}).addTo(" + clientId + "_map);", null);
		rw.writeText("}", null);
		rw.endElement("script");
	}

}
