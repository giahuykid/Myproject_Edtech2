import axios from 'axios';

const API_BASE_URL = '/api';

export interface FileResponse {
  id: number;
  fileName: string;
  fileType: string;
  size: number;
  language: string;
  uploadedBy: string;
  uploadDate: string;
  uploadStatus: string;
  message: string;
}

export const getFiles = async (): Promise<FileResponse[]> => {
  const response = await fetch(`${API_BASE_URL}/files`, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
    },
  });

  if (!response.ok) {
    if (response.status === 404) {
      return []; // Return empty array if no files found
    }
    const errorText = await response.text();
    throw new Error(errorText || 'Failed to fetch files');
  }

  return response.json();
};

export const getAllFiles = async (language?: string): Promise<FileResponse[]> => {
  const params = new URLSearchParams();
  if (language) {
    params.append('language', language);
  }
  try {
    // Try the new endpoint first
    const response = await axios.get(`${API_BASE_URL}/files/all?${params.toString()}`);
    return response.data;
  } catch (error) {
    // If the new endpoint fails, try the legacy endpoint
    const response = await axios.get(`${API_BASE_URL}/files?${params.toString()}`);
    return response.data;
  }
};

export const uploadFiles = async (
  files: File[],
  uploadedBy: string,
  language: string
): Promise<FileResponse[]> => {
  const formData = new FormData();
  files.forEach(file => formData.append('files', file));
  formData.append('uploadedBy', uploadedBy);
  formData.append('language', language);

  const response = await fetch(`${API_BASE_URL}/files/upload`, {
    method: 'POST',
    body: formData,
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || 'Failed to upload files');
  }

  return response.json();
};

export const getFileInfo = async (fileId: number): Promise<FileResponse> => {
  const response = await axios.get(`${API_BASE_URL}/files/${fileId}/info`);
  return response.data;
};

export const viewFile = async (fileId: number): Promise<Blob> => {
  const response = await fetch(`${API_BASE_URL}/files/${fileId}/content`, {
    method: 'GET',
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || 'Failed to view file');
  }

  return response.blob();
};

export const updateFile = async (
  fileId: number,
  file: File,
  uploadedBy: string = 'system',
  language: string
): Promise<FileResponse> => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('uploadedBy', uploadedBy);
  formData.append('language', language);

  const response = await axios.put(`${API_BASE_URL}/files/${fileId}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return response.data;
};

export const deleteFile = async (fileId: number): Promise<void> => {
  const response = await fetch(`${API_BASE_URL}/files/${fileId}`, {
    method: 'DELETE',
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || 'Failed to delete file');
  }
}; 