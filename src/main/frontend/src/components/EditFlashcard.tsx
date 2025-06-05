import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Box
} from '@mui/material';
import { Flashcard, updateFlashcard } from '../services/flashcardService';

interface EditFlashcardProps {
  open: boolean;
  onClose: () => void;
  flashcard: Flashcard;
  collectionId: number;
  onUpdate: (updatedFlashcard: Flashcard) => void;
}

const EditFlashcard: React.FC<EditFlashcardProps> = ({
  open,
  onClose,
  flashcard,
  collectionId,
  onUpdate
}) => {
  const [word, setWord] = useState(flashcard.word);
  const [meaning, setMeaning] = useState(flashcard.meaning);
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setIsSubmitting(true);

    try {
      const updatedFlashcard = await updateFlashcard(
        collectionId,
        flashcard.id,
        word,
        meaning
      );
      onUpdate(updatedFlashcard);
      onClose();
    } catch (err) {
      setError('Failed to update flashcard. Please try again.');
      console.error('Error updating flashcard:', err);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>Edit Flashcard</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField
              label="Word"
              value={word}
              onChange={(e) => setWord(e.target.value)}
              fullWidth
              required
              error={error !== null}
              disabled={isSubmitting}
            />
            <TextField
              label="Meaning"
              value={meaning}
              onChange={(e) => setMeaning(e.target.value)}
              fullWidth
              required
              multiline
              rows={3}
              error={error !== null}
              disabled={isSubmitting}
              helperText={error}
            />
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose} disabled={isSubmitting}>
            Cancel
          </Button>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            disabled={isSubmitting}
          >
            Update
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
};

export default EditFlashcard; 