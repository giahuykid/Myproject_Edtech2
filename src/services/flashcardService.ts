import axios from "axios";
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://20.89.64.149:8080';

const api = axios.create({
  baseURL: `${API_BASE_URL}/api/flashcard`,
  headers: {
    'Content-Type': 'application/json',
  },
});

export interface Flashcard {
  id: number;
  word: string;
  meaning: string;
  collectionId: number;
}

export interface FlashcardCollection {
  id: number;
  name: string;
  numberOfFlashcards: number;
  userId: number;
  flashcards: Flashcard[];
}

export const getAllFlashcardCollections = async (): Promise<FlashcardCollection[]> => {
  try {
    const response = await api.get('/collections');
    return response.data;
  } catch (error) {
    console.error('Error fetching collections:', error);
    throw error;
  }
};

export const getFlashcardCollection = async (id: number): Promise<FlashcardCollection> => {
  const response = await api.get(`/getflashcardcollection/${id}`);
  return response.data;
};

export const createFlashcardCollection = async (name: string): Promise<FlashcardCollection> => {
  const params = new URLSearchParams();
  params.append('name', name);
  params.append('userId', '1');

  const response = await api.post(`/createflashcardcollection?${params.toString()}`);
  return response.data;
};

export const deleteFlashcardCollection = async (id: number): Promise<void> => {
  await api.delete(`/deletecollection/${id}`);
};

export const addFlashcard = async (collectionId: number, word: string, meaning: string): Promise<Flashcard> => {
  const params = new URLSearchParams();
  params.append('word', word);
  params.append('meaning', meaning);
  params.append('collectionId', collectionId.toString());

  const response = await api.post(`/createflashcard?${params.toString()}`);
  return response.data;
};

export const updateFlashcard = async (flashcardId: number, word: string, meaning: string): Promise<Flashcard> => {
  const params = new URLSearchParams();
  params.append('word', word);
  params.append('meaning', meaning);

  const response = await api.patch(`/updateflashcard/${flashcardId}?${params.toString()}`);
  return response.data;
};

export const deleteFlashcard = async (collectionId: number, flashcardId: number): Promise<void> => {
  await api.delete(`/deleteflashcard/${flashcardId}`);
};