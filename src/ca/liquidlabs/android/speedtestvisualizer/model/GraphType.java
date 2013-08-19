/*
 * Copyright 2013 Liquid Labs Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.liquidlabs.android.speedtestvisualizer.model;

/**
 * List of graph type supported by the app. More types can be added here, and
 * used in the app.
 * <p>
 * Type naming convention: <code>[X-AXIS-METRIC(s)]_VS_[Y-AXIS-METRIC]</code>
 * </p>
 * 
 * @author Hossain Khan
 */
public enum GraphType {
    DATE_VS_DOWNLOAD,
    DATE_VS_UPLOAD,
    DATE_VS_DOWNLOAD_UPLOAD,
    DATE_VS_DOWNLOAD_UPLOAD_LATENCY;
}