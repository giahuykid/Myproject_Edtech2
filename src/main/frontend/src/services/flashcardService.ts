import axios from 'axios';

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

const API_BASE_URL = 'http://localhost:8080/api/flashcard';

export const getAllFlashcardCollections = async (): Promise<FlashcardCollection[]> => {
  const response = await axios.get(`${API_BASE_URL}/collections`);
  return response.data;
};

export const getFlashcardCollection = async (id: number): Promise<FlashcardCollection> => {
  const response = await axios.get(`${API_BASE_URL}/getflashcardcollection/${id}`);
  return response.data;
};

export const createFlashcardCollection = async (name: string): Promise<FlashcardCollection> => {
  const params = new URLSearchParams();
  params.append('name', name);
  params.append('userId', '1');

  const response = await axios.post(`${API_BASE_URL}/createflashcardcollection?${params.toString()}`);
  return response.data;
};

export const deleteFlashcardCollection = async (id: number): Promise<void> => {
  await axios.delete(`${API_BASE_URL}/deletecollection/${id}`);
};

export const addFlashcard = async (collectionId: number, word: string, meaning: string): Promise<Flashcard> => {
  const params = new URLSearchParams();
  params.append('word', word);
  params.append('meaning', meaning);
  params.append('collectionId', collectionId.toString());

  const response = await axios.post(`${API_BASE_URL}/createflashcard?${params.toString()}`);
  return response.data;
};

export const updateFlashcard = async (collectionId: number, flashcardId: number, word: string, meaning: string): Promise<Flashcard> => {
  const params = new URLSearchParams();
  params.append('word', word);
  params.append('meaning', meaning);

  const response = await axios.patch(`${API_BASE_URL}/updateflashcard/${flashcardId}?${params.toString()}`);
  return response.data;
};

export const deleteFlashcard = async (collectionId: number, flashcardId: number): Promise<void> => {
  await axios.delete(`${API_BASE_URL}/deleteflashcard/${flashcardId}`);
}; 