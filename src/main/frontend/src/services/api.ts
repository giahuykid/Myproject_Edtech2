// src/services/api.ts
import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api/mock',
    headers: {
        'Content-Type': 'application/json',
    },
});


export interface QuizAnswerDTO {
    id?: number;
    answer: string;
    correct: boolean;
    point: number;
}

export interface QuizQuestionDTO {
    id?: number;
    question: string;
    answers: QuizAnswerDTO[];
}

export interface ScoreDTO {
    id: number;
    score: number;
    submittedAt: string;
    userId: number;
    mockId: number;
}

export interface MockDTO {
    id: number;
    nameMock: string;
    languageName: string;
    numberOfQuestions: number;
    questions: QuizQuestionDTO[];
    scores: ScoreDTO[];
}
// Add to src/services/api.ts
export interface LanguageDTO {
    id: number;
    languageName: string;
}

// Add this function to fetch languages
export const getAllLanguages = async (): Promise<LanguageDTO[]> => {
    try {
        const response = await api.get<LanguageDTO[]>('/languages');
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.message || 'Failed to fetch languages');
    }
};

// Modified createMock to ensure correct answer flag is properly set


export const getMockById = async (mockId: number): Promise<MockDTO> => {
    try {
        const response = await api.get<MockDTO>(`/getMock/${mockId}`);
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.message || 'Failed to fetch quiz');
    }
};
export const getCurrentScore = async (mockId: number): Promise<number> => {
    try {
        const response = await api.get<number>(`/${mockId}/score`);
        return response.data;
    } catch (error: any) {
        console.error('Failed to fetch current score:', error);
        throw new Error(error.response?.data?.message || 'Failed to fetch current score');
    }
};


export const verifyAnswer = async (
    mockId: number,
    questionId: number,
    answerId: number
): Promise<void> => {
    try {
        // Get the current question and answer details
        const mock = await getMockById(mockId);
        const question = mock.questions.find(q => q.id === questionId);
        const answer = question?.answers.find(a => a.id === answerId);

        console.log('Verification details:', {
            mockId,
            questionId,
            answerId,
            question,
            answer,
            isCorrect: answer?.correct
        });
    } catch (error) {
        console.error('Verification error:', error);
    }
};// src/services/api.ts


// Modified submitAnswer to handle the correct property
export const submitAnswer = async (
    mockId: number,
    questionId: number,
    answerId: number
): Promise<boolean> => {
    try {
        // Get current answer details
        const mock = await getMockById(mockId);
        const question = mock.questions.find(q => q.id === questionId);
        const answer = question?.answers.find(a => a.id === answerId);

        console.log('Answer details:', {
            answer,
            isCorrect: answer?.correct  // Use correct instead of isCorrect
        });

        const response = await api.post<boolean>(`/${mockId}/submit`, null, {
            params: {
                questionId,
                answerId
            }
        });

        return response.data;
    } catch (error: any) {
        console.error('Submit answer error:', error.response?.data || error.message);
        throw new Error(error.response?.data?.message || 'Failed to submit answer');
    }
};

// Update createMock to handle the correct property
export const createMock = async (
    nameMock: string,
    userId: number,
    languageId: number,
    numberOfQuestions: number,
    questions: QuizQuestionDTO[]
): Promise<MockDTO> => {
    try {
        // Map the questions to ensure correct property is used
        const formattedQuestions = questions.map(q => ({
            ...q,
            answers: q.answers.map(a => ({
                ...a,
                correct: a.correct  // Map isCorrect to correct
            }))
        }));

        const response = await api.post<MockDTO>('/create', formattedQuestions, {
            params: { nameMock, userId, languageId, numberOfQuestions },
        });

        return response.data;
    } catch (error: any) {
        console.error('Create mock error:', error.response?.data || error);
        throw new Error(error.response?.data?.message || 'Failed to create quiz');
    }
};export const getAllMocks = async (): Promise<MockDTO[]> => {
    try {
        const response = await api.get<MockDTO[]>('/all');
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.message || 'Failed to fetch mocks');
    }
};
// src/services/api.ts

// Add delete function
export const deleteMock = async (mockId: number): Promise<void> => {
    try {
        await api.delete(`/delete/${mockId}`);
    } catch (error: any) {
        throw new Error(error.response?.data?.message || 'Failed to delete quiz');
    }
};

// src/services/api.ts
// src/services/api.ts
export const updateMock = async (
    mockId: number,
    updateData: {
        languageId?: number;
        numberOfQuestions?: number;
        questions?: QuizQuestionDTO[];
    }
): Promise<MockDTO> => {
    try {
        const response = await api.patch<MockDTO>(`/update/${mockId}`, {
            languageId: updateData.languageId,
            numberOfQuestions: updateData.numberOfQuestions,
            questions: updateData.questions
        });
        return response.data;
    } catch (error: any) {
        throw new Error(error.response?.data?.message || 'Failed to update quiz');
    }
};


export default api;